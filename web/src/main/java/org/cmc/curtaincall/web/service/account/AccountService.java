package org.cmc.curtaincall.web.service.account;

import lombok.RequiredArgsConstructor;
import org.cmc.curtaincall.domain.account.Account;
import org.cmc.curtaincall.domain.account.MemberId;
import org.cmc.curtaincall.domain.account.repository.AccountRepository;
import org.cmc.curtaincall.web.exception.AlreadySignupAccountException;
import org.cmc.curtaincall.web.exception.EntityNotFoundException;
import org.cmc.curtaincall.web.service.account.response.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    public Long getMemberId(String username) {
        Account account = getAccountByUsername(username);
        return Optional.ofNullable(account.getMemberId())
                .map(MemberId::getId)
                .orElse(null);
    }

    @Transactional
    public AccountDto login(String username, String refreshToken, LocalDateTime refreshTokenExpiresAt) {
        Account account = getOrCreate(username);
        account.renewRefreshToken(refreshToken, refreshTokenExpiresAt);
        return AccountDto.of(account);
    }

    @Transactional
    public void logout(String username) {
        Account account = getAccountByUsername(username);
        account.refreshTokenExpires();
    }

    private Account getOrCreate(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsernameAndUseYnIsTrue(username);
        if (accountOptional.isEmpty()) {
            Account account = accountRepository.save(new Account(username));
            accountOptional = Optional.of(account);
        }
        Account account = accountOptional.get();
        if (Boolean.FALSE.equals(account.getUseYn())) {
            throw new EntityNotFoundException("deleted Account.username=" + username);
        }
        return account;
    }

    public AccountDto get(String username) {
        Account account = getAccountByUsername(username);
        return AccountDto.of(account);
    }

    @Transactional
    public void signupMember(String username, Long memberId) {
        Account account = getAccountByUsername(username);
        if (account.getMemberId() != null) {
            throw new AlreadySignupAccountException("username=" + username);
        }
        account.registerMember(new MemberId(memberId));
    }

    @Transactional
    public void delete(MemberId memberId) {
        Account account = getAccountByMember(memberId);
        accountRepository.delete(account);
    }

    private Account getAccountByUsername(String username) {
        return accountRepository.findByUsernameAndUseYnIsTrue(username)
                .orElseThrow(() -> new EntityNotFoundException("Account.username=" + username));
    }

    private Account getAccountByMember(MemberId memberId) {
        return accountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Account.username=" + memberId));
    }
}
