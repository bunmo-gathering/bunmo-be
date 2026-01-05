package io.github.bunmo.gathering;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gathering")
public class Gathering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GatheringDetail detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    @JoinColumn(name = "owner_id", nullable = false)
    private Long owner_id;

    @OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GatheringParticipant> participants = new ArrayList<>();

    @Column(name = "max_participant_count", nullable = false)
    private int maxParticipantCount;

    @Embedded
    private GatheringLocation location;

    @CreatedDate
    @Column(name = "gathering_time", nullable = false)
    private LocalDateTime gatheringTime;
}
