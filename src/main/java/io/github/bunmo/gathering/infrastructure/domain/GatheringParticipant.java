package io.github.bunmo.gathering.infrastructure.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "gathering_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatheringParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id", nullable = false)
    private Gathering gathering;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    public static GatheringParticipant create(Gathering gathering, Long memberId) {
        GatheringParticipant participant = new GatheringParticipant();
        participant.gathering = gathering;
        participant.memberId = memberId;
        participant.joinedAt = LocalDateTime.now();
        return participant;
    }
}
