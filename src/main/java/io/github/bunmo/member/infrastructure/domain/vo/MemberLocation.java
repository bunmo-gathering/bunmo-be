package io.github.bunmo.member.infrastructure.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record MemberLocation(
        @Column(precision = 17, scale = 14)
        BigDecimal x,
        @Column(precision = 16, scale = 14)
        BigDecimal y,
        String address
) {
}
