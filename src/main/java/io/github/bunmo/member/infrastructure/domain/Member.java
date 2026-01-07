package io.github.bunmo.member.infrastructure.domain;

import io.github.bunmo.member.infrastructure.domain.enums.ActiveType;
import io.github.bunmo.member.infrastructure.domain.enums.AuthType;
import io.github.bunmo.member.infrastructure.domain.enums.RoleType;
import io.github.bunmo.member.infrastructure.domain.vo.MemberLocation;
import io.github.bunmo.member.infrastructure.domain.vo.MemberProfile;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Embedded
    private MemberProfile memberProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", nullable = false)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_type", nullable = false)
    private ActiveType activeType;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Embedded
    private MemberLocation location;

    @Column(nullable = false)
    private int gatheringOpenCount;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    public String email() {
        return this.email;
    }

    public RoleType role() {
        return this.role;
    }
}
