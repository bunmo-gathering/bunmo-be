package io.github.bunmo.gathering.dto.request;

import io.github.bunmo.gathering.infrastructure.domain.enums.CategoryType;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateGatheringRequest(
        @NotNull(message = "모임 유형은 필수입니다.")
        GatheringType type,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 20, message = "제목은 최대 20자입니다.")
        String title,

        @DecimalMax(value = "1000000", message = "가격은 100만원 미만이어야 합니다.")
        @DecimalMin(value = "0", message = "가격은 0 이상이어야 합니다.")
        BigDecimal price,

        @Size(max = 200, message = "상품 링크는 최대 200자입니다.")
        String productLink,

        @NotNull(message = "날짜는 필수입니다.")
        @FutureOrPresent(message = "날짜는 현재 또는 미래여야 합니다.")
        LocalDate meetingDate,

        @NotNull(message = "시간은 필수입니다.")
        LocalTime meetingTime,

        @NotBlank(message = "장소는 필수입니다.")
        @Size(max = 20, message = "장소는 최대 20자입니다.")
        String location,

        @NotBlank(message = "상세 소개말은 필수입니다.")
        @Size(max = 500, message = "상세 소개말은 최대 500자입니다.")
        String description,

        @NotBlank(message = "오픈채팅 링크는 필수입니다.")
        @Size(max = 200, message = "오픈채팅 링크는 최대 200자입니다.")
        String openChatLink,

        @NotNull(message = "카테고리는 필수입니다.")
        CategoryType category,

        @NotNull(message = "최대 인원은 필수입니다.")
        @Min(value = 2, message = "최대 인원은 2명 이상이어야 합니다.")
        @Max(value = 8, message = "최대 인원은 8명 이하여야 합니다.")
        Integer maxParticipants
) {
}
