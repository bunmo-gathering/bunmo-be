package io.github.bunmo.member.infrastructure.repository;

import io.github.bunmo.member.infrastructure.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
