package io.github.bunmo.gathering.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record GatheringListPageResponse(
        List<GatheringListResponse> contents,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static GatheringListPageResponse from(Page<GatheringListResponse> page) {
        return new GatheringListPageResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
