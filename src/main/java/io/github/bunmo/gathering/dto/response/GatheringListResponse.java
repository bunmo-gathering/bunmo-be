package io.github.bunmo.gathering.dto.response;

import io.github.bunmo.gathering.infrastructure.domain.Gathering;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;

public record GatheringListResponse(
        Long id,
        GatheringType type,
        String title,
        String thumbnailUrl,
        LocalDate meetingDate,
        LocalTime meetingTime,
        String location,
        BigDecimal pricePerPerson,
        int currentParticipants,
        int maxParticipants
) {
    public static GatheringListResponse from(Gathering gathering) {
        int participantCount = gathering.getParticipants().size();
        BigDecimal pricePerPerson = null;
        if (gathering.getPrice() != null && participantCount > 0) {
            pricePerPerson = gathering.getPrice().divide(
                    BigDecimal.valueOf(participantCount), 0, RoundingMode.CEILING
            );
        }

        return new GatheringListResponse(
                gathering.getId(),
                gathering.getType(),
                gathering.getDetail().name(),
                null,
                gathering.getMeetingDate(),
                gathering.getMeetingTime(),
                gathering.getLocation().address(),
                pricePerPerson,
                participantCount,
                gathering.getMaxParticipantCount()
        );
    }
}
