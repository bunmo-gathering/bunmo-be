package io.github.bunmo.gathering.infrastructure.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record GatheringDetail(
        @Column(nullable = false, length = 20)
        String name,
        @Column(nullable = false, length = 500)
        String introduction,
        @Column(name = "open_chat_link", nullable = false, length = 200)
        String openChatLink
) {
}
