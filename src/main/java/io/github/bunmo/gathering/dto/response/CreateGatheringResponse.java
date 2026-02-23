package io.github.bunmo.gathering.dto.response;

import io.github.bunmo.gathering.infrastructure.domain.Gathering;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;

import java.time.LocalDateTime;

public record CreateGatheringResponse(
        Long id,
        GatheringType type,
        String title,
        Long hostId,
        LocalDateTime createdAt
) {
    public static CreateGatheringResponse from(Gathering gathering) {
        return new CreateGatheringResponse(
                gathering.getId(),
                gathering.getType(),
                gathering.getDetail().name(),
                gathering.getOwnerId(),
                gathering.getCreatedAt()
        );
    }
}
