package io.github.bunmo.member;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Member {
    private Long id;
    private String nickname;
    private Gender gender;
    private String profileImageUrl;
    private AuthType authType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ActiveType activeType;
    private String location;
    private BigDecimal x;
    private BigDecimal y;
    private int gatheringOpenCount;
}
