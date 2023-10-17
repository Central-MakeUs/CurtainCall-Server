package org.cmc.curtaincall.web.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cmc.curtaincall.domain.member.Member;
import org.cmc.curtaincall.web.security.UserWithMemberId;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// TODO JDBC 템플릿 사용하도록 변경
@RequiredArgsConstructor
@Slf4j
public class LoginMemberAuditorAware implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(UserWithMemberId.class::isInstance)
                .map(details -> ((UserWithMemberId) details).getMemberId())
                .map(Member::new);
    }
}
