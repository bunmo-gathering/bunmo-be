package io.github.bunmo.gathering;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record GatheringLocation(
        @Column(nullable = false)
        BigDecimal x,
        @Column(nullable = false)
        BigDecimal y,
        @Column(nullable = false)
        String address
) {
}
