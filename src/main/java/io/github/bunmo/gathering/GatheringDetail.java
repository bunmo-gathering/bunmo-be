package io.github.bunmo.gathering;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record GatheringDetail(
        @Column(nullable = false)
        String name,
        @Column(nullable = false)
        String introduction,
        @Column(name = "open_chat_link", nullable = false)
        String openChatLink
) {
}
