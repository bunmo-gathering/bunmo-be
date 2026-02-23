package io.github.bunmo.gathering.dto.response;

import io.github.bunmo.gathering.infrastructure.domain.Gathering;
import io.github.bunmo.gathering.infrastructure.domain.GatheringParticipant;
import io.github.bunmo.gathering.infrastructure.domain.enums.CategoryType;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;
import io.github.bunmo.member.infrastructure.domain.Member;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record GatheringDetailResponse(
        Long id,
        GatheringType type,
        String title,
        BigDecimal price,
        String productLink,
        LocalDate meetingDate,
        LocalTime meetingTime,
        String location,
        String description,
        String openChatLink,
        CategoryType category,
        int maxParticipants,
        int currentParticipants,
        HostInfo host,
        List<ParticipantInfo> participants,
        boolean isHost,
        String remainingTime,
        LocalDateTime createdAt
) {
    public record HostInfo(Long id, String nickname, String profileImageUrl) {
        public static HostInfo from(Member member) {
            return new HostInfo(
                    member.getId(),
                    member.getMemberProfile().nickname(),
                    member.getMemberProfile().profileImageUrl()
            );
        }
    }

    public record ParticipantInfo(Long id, String nickname, String profileImageUrl) {
        public static ParticipantInfo from(Member member) {
            return new ParticipantInfo(
                    member.getId(),
                    member.getMemberProfile().nickname(),
                    member.getMemberProfile().profileImageUrl()
            );
        }
    }

    public static GatheringDetailResponse of(Gathering gathering, Member host, List<Member> participantMembers, Long currentMemberId) {
        String remainingTime = calculateRemainingTime(gathering.getMeetingDate(), gathering.getMeetingTime());
        List<ParticipantInfo> participantInfos = participantMembers.stream()
                .map(ParticipantInfo::from)
                .toList();

        return new GatheringDetailResponse(
                gathering.getId(),
                gathering.getType(),
                gathering.getDetail().name(),
                gathering.getPrice(),
                gathering.getProductLink(),
                gathering.getMeetingDate(),
                gathering.getMeetingTime(),
                gathering.getLocation().address(),
                gathering.getDetail().introduction(),
                gathering.getDetail().openChatLink(),
                gathering.getCategory(),
                gathering.getMaxParticipantCount(),
                gathering.getParticipants().size(),
                HostInfo.from(host),
                participantInfos,
                gathering.getOwnerId().equals(currentMemberId),
                remainingTime,
                gathering.getCreatedAt()
        );
    }

    private static String calculateRemainingTime(LocalDate meetingDate, LocalTime meetingTime) {
        LocalDateTime meetingDateTime = LocalDateTime.of(meetingDate, meetingTime);
        LocalDateTime now = LocalDateTime.now();
        if (meetingDateTime.isBefore(now)) {
            return "모임 시작됨";
        }
        long hours = ChronoUnit.HOURS.between(now, meetingDateTime);
        if (hours < 24) {
            return hours + "시간";
        }
        long days = ChronoUnit.DAYS.between(now, meetingDateTime);
        return days + "일";
    }
}
