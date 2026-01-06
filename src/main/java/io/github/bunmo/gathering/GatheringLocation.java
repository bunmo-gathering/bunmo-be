package io.github.bunmo.gathering;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record GatheringLocation(
        @Column(nullable = false, precision = 17, scale = 14)
        BigDecimal x,
        @Column(nullable = false, precision = 16, scale = 14)
        BigDecimal y,
        @Column(nullable = false)
        String address
) {
}
