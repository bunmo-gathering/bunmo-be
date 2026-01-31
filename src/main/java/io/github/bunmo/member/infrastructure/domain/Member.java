package io.github.bunmo.member.infrastructure.domain;

import io.github.bunmo.member.infrastructure.domain.enums.ActiveType;
import io.github.bunmo.member.infrastructure.domain.enums.RoleType;
import io.github.bunmo.member.infrastructure.domain.vo.MemberLocation;
import io.github.bunmo.member.infrastructure.domain.vo.MemberProfile;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Embedded
    private MemberProfile memberProfile;

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
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Member createPendingMember() {
        Member member = new Member();
        member.uuid = UUID.randomUUID().toString();
        member.activeType = ActiveType.PENDING;
        member.role = RoleType.MEMBER;
        member.location = null;
        member.gatheringOpenCount = 0;
        return member;
    }

    public void completeOnboarding(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
        this.activeType = ActiveType.ACTIVE;
    }

    public RoleType role() {
        return this.role;
    }

    public boolean validateMemberStatus() {
        return activeType.isValid();
    }

    public boolean isNewMember() {
        return activeType == ActiveType.PENDING;
    }
}
