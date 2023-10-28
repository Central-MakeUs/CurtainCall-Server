package org.cmc.curtaincall.web.review;

import lombok.RequiredArgsConstructor;
import org.cmc.curtaincall.domain.core.CreatorId;
import org.cmc.curtaincall.domain.member.MemberId;
import org.cmc.curtaincall.domain.review.dao.ShowReviewDao;
import org.cmc.curtaincall.domain.review.response.ShowReviewMyResponse;
import org.cmc.curtaincall.domain.review.response.ShowReviewResponse;
import org.cmc.curtaincall.domain.show.ShowId;
import org.cmc.curtaincall.web.common.response.ListResult;
import org.cmc.curtaincall.web.security.LoginMemberId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShowReviewQueryController {

    private final ShowReviewDao showReviewDao;

    @GetMapping("/shows/{showId}/reviews")
    public ListResult<ShowReviewResponse> getList(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String showId) {
        return new ListResult<>(showReviewDao.getList(pageable, new ShowId(showId)));
    }

    @GetMapping("/member/reviews")
    public ListResult<ShowReviewMyResponse> getMyList(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @LoginMemberId MemberId memberId
    ) {
        return new ListResult<>(showReviewDao.getMyList(pageable, new CreatorId(memberId)));
    }

}
