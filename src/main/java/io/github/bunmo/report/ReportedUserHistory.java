package io.github.bunmo.report;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reported_user_history")
public class ReportedUserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    @Column(name = "reportee_id", nullable = false)
    private Long reporteeId;

    @Column(name = "reported_date", nullable = false)
    private LocalDate reportedDate;
}
