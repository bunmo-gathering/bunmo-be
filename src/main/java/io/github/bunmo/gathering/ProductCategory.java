package io.github.bunmo.gathering;

import io.github.bunmo.member.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductCategory {
    private Long id;
    private Member owner;
    private List<Member> participants;
    private String name;
    private ProductCategory productCategory;
    private LocalDateTime gatheringTime;
    private String detailAddress;
    private BigDecimal x;
    private BigDecimal y;
    private String introduction;
    private String openChatLink;
    private int maxParticipantsCount;
}
