package io.github.bunmo.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record MemberLocation(
        @Column(nullable = false)
        BigDecimal x,
        @Column(nullable = false)
        BigDecimal y,
        @Column(nullable = false)
        String address
) {
}
