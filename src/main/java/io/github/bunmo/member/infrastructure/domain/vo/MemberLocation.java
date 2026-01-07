package io.github.bunmo.member.infrastructure.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record MemberLocation(
        @Column(nullable = false, precision = 17, scale = 14)
        BigDecimal x,
        @Column(nullable = false, precision = 16, scale = 14)
        BigDecimal y,
        @Column(nullable = false)
        String address
) {
}
