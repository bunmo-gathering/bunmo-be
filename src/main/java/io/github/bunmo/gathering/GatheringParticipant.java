package io.github.bunmo.gathering;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gathering_participant")
public class GatheringParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id", nullable = false)
    private Gathering gathering;

    @JoinColumn(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
