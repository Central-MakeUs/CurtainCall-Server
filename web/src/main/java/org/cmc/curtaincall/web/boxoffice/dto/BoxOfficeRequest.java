package org.cmc.curtaincall.web.boxoffice.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.cmc.curtaincall.domain.show.BoxOfficeGenre;
import org.cmc.curtaincall.domain.show.BoxOfficeType;

import java.time.LocalDate;

public record BoxOfficeRequest(
        @NotNull BoxOfficeType type,
        @NotNull LocalDate baseDate,
        @Nullable BoxOfficeGenre genre,
        @Nullable String areaCode
) {
}
