package io.github.bunmo.gathering.infrastructure.domain;

import io.github.bunmo.gathering.infrastructure.domain.enums.ActiveType;
import io.github.bunmo.gathering.infrastructure.domain.enums.CategoryType;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gathering")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private GatheringType type;

    @Embedded
    private GatheringDetail detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private CategoryType category;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GatheringParticipant> participants = new ArrayList<>();

    @Column(name = "max_participant_count", nullable = false)
    private int maxParticipantCount;

    @Embedded
    private GatheringLocation location;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_link", length = 200)
    private String productLink;

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @Column(name = "meeting_time", nullable = false)
    private LocalTime meetingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_type", nullable = false, length = 10)
    private ActiveType activeType;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static Gathering create(
            Long ownerId,
            GatheringType type,
            GatheringDetail detail,
            CategoryType category,
            int maxParticipantCount,
            GatheringLocation location,
            BigDecimal price,
            String productLink,
            LocalDate meetingDate,
            LocalTime meetingTime
    ) {
        Gathering gathering = new Gathering();
        gathering.ownerId = ownerId;
        gathering.type = type;
        gathering.detail = detail;
        gathering.category = category;
        gathering.maxParticipantCount = maxParticipantCount;
        gathering.location = location;
        gathering.price = price;
        gathering.productLink = productLink;
        gathering.meetingDate = meetingDate;
        gathering.meetingTime = meetingTime;
        gathering.activeType = ActiveType.ACTIVE;
        return gathering;
    }
}
