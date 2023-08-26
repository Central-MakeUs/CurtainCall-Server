package org.cmc.curtaincall.web.service.party.response;


import lombok.*;
import org.cmc.curtaincall.domain.image.Image;
import org.cmc.curtaincall.domain.party.Party;
import org.cmc.curtaincall.domain.party.PartyCategory;
import org.cmc.curtaincall.domain.show.Show;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PartyDetailResponse {

    private Long id;

    private String title;

    private String content;

    private PartyCategory category;

    private Integer curMemberNum;

    private Integer maxMemberNum;

    private LocalDateTime showAt;

    private Long creatorId;

    private LocalDateTime createdAt;

    private String creatorNickname;

    private String creatorImageUrl;

    private String showId;

    private String showName;

    private String showPoster;

    private String facilityId;

    private String facilityName;

    public static PartyDetailResponse of(Party party) {
        Optional<Show> showOptional = Optional.ofNullable(party.getShow());
        return PartyDetailResponse.builder()
                .id(party.getId())
                .title(party.getTitle())
                .content(party.getContent())
                .category(party.getCategory())
                .curMemberNum(party.getCurMemberNum())
                .maxMemberNum(party.getMaxMemberNum())
                .showAt(party.getShowAt())
                .createdAt(party.getCreatedAt())
                .creatorId(party.getCreatedBy().getId())
                .creatorNickname(party.getCreatedBy().getNickname())
                .creatorImageUrl(Optional.ofNullable(party.getCreatedBy().getImage())
                        .filter(Image::getUseYn)
                        .map(Image::getUrl)
                        .orElse(null))
                .showId(showOptional.map(Show::getId).orElse(null))
                .showName(showOptional.map(Show::getName).orElse(null))
                .showPoster(showOptional.map(Show::getPoster).orElse(null))
                .facilityId(showOptional.map(show -> show.getFacility().getId()).orElse(null))
                .facilityName(showOptional.map(show -> show.getFacility().getName()).orElse(null))
                .build();
    }
}
