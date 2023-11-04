package org.cmc.curtaincall.domain.party.response;


import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.cmc.curtaincall.domain.core.CreatorId;
import org.cmc.curtaincall.domain.party.PartyCategory;

import java.time.LocalDateTime;

@Getter
@ToString
public class PartyDetailResponse {

    private Long id;

    private String title;

    private String content;

    private PartyCategory category;

    private Integer curMemberNum;

    private Integer maxMemberNum;

    private CreatorId creatorId;

    private LocalDateTime createdAt;

    private String creatorNickname;

    @Nullable
    private String creatorImageUrl;

    @Nullable
    private String showId;

    @Nullable
    private String showName;

    @Nullable
    private String showPoster;

    @Nullable
    private LocalDateTime showAt;

    @Nullable
    private String facilityId;

    @Nullable
    private String facilityName;

    @Builder
    @QueryProjection
    public PartyDetailResponse(
            Long id,
            String title,
            String content,
            PartyCategory category,
            Integer curMemberNum,
            Integer maxMemberNum,
            LocalDateTime createdAt,
            CreatorId creatorId,
            String creatorNickname,
            @Nullable String creatorImageUrl,
            @Nullable String showId,
            @Nullable String showName,
            @Nullable String showPoster,
            @Nullable LocalDateTime showAt,
            @Nullable String facilityId,
            @Nullable String facilityName
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.curMemberNum = curMemberNum;
        this.maxMemberNum = maxMemberNum;
        this.showAt = showAt;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.creatorNickname = creatorNickname;
        this.creatorImageUrl = creatorImageUrl;
        this.showId = showId;
        this.showName = showName;
        this.showPoster = showPoster;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
    }
}
