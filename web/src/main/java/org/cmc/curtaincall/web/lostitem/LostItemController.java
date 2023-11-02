package org.cmc.curtaincall.web.lostitem;

import lombok.RequiredArgsConstructor;
import org.cmc.curtaincall.domain.member.MemberId;
import org.cmc.curtaincall.web.common.response.IdResult;
import org.cmc.curtaincall.web.exception.EntityAccessDeniedException;
import org.cmc.curtaincall.web.lostitem.request.LostItemCreate;
import org.cmc.curtaincall.web.lostitem.request.LostItemEdit;
import org.cmc.curtaincall.web.security.LoginMemberId;
import org.cmc.curtaincall.web.service.image.ImageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LostItemController {

    private final LostItemService lostItemService;

    private final ImageService imageService;

    @PostMapping("/lostItems")
    public IdResult<Long> createLostItem(
            @RequestBody @Validated LostItemCreate lostItemCreate, @LoginMemberId MemberId memberId
    ) {
        if (!imageService.isOwnedByMember(memberId.getId(), lostItemCreate.getImageId())) {
            throw new EntityAccessDeniedException(
                    "Member ID=" + memberId + ", Image ID=" + lostItemCreate.getImageId());
        }
        return lostItemService.create(lostItemCreate);
    }

    @DeleteMapping("/lostItems/{lostItemId}")
    public void deleteLostItem(@PathVariable Long lostItemId, @LoginMemberId MemberId memberId) {
        if (!lostItemService.isOwnedByMember(lostItemId, memberId.getId())) {
            throw new EntityAccessDeniedException("lostItemId=" + lostItemId + "memberId=" + memberId);
        }
        lostItemService.delete(lostItemId);
    }

    @PatchMapping("/lostItems/{lostItemId}")
    public void editLostItem(
            @PathVariable Long lostItemId, @LoginMemberId MemberId memberId,
            @RequestBody @Validated LostItemEdit lostItemEdit) {
        if (!lostItemService.isOwnedByMember(lostItemId, memberId.getId())) {
            throw new EntityAccessDeniedException("lostItemId=" + lostItemId + "memberId=" + memberId);
        }
        if (!imageService.isOwnedByMember(memberId.getId(), lostItemEdit.getImageId())) {
            throw new EntityAccessDeniedException(
                    "Member ID=" + memberId + ", Image ID=" + lostItemEdit.getImageId());
        }
        lostItemService.edit(lostItemId, lostItemEdit);
    }
}
