package io.github.bunmo.gathering.controller;

import io.github.bunmo.common.web.ApiResponse;
import io.github.bunmo.gathering.dto.GatheringResultCode;
import io.github.bunmo.gathering.dto.request.CreateGatheringRequest;
import io.github.bunmo.gathering.dto.response.CreateGatheringResponse;
import io.github.bunmo.gathering.dto.response.GatheringDetailResponse;
import io.github.bunmo.gathering.dto.response.GatheringListPageResponse;
import io.github.bunmo.gathering.service.GatheringService;
import io.github.bunmo.security.CustomUserDetails;
import io.github.bunmo.security.annotation.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/gatherings")
@RequiredArgsConstructor
public class GatheringController {

    private final GatheringService gatheringService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateGatheringResponse>> createGathering(
            @Valid @RequestBody CreateGatheringRequest request,
            @LoginUser CustomUserDetails userDetails
    ) {
        CreateGatheringResponse response = gatheringService.createGathering(request, userDetails);
        return ResponseEntity.status(GatheringResultCode.GATHERING_CREATE_SUCCESS.statusCode())
                .body(ApiResponse.success(GatheringResultCode.GATHERING_CREATE_SUCCESS, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<GatheringListPageResponse>> getGatherings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        GatheringListPageResponse response = gatheringService.getGatherings(page, size, date);
        return ResponseEntity.ok(ApiResponse.success(GatheringResultCode.GATHERING_LIST_SUCCESS, response));
    }

    @GetMapping("/{gatheringId}")
    public ResponseEntity<ApiResponse<GatheringDetailResponse>> getGathering(
            @PathVariable Long gatheringId,
            @LoginUser CustomUserDetails userDetails
    ) {
        GatheringDetailResponse response = gatheringService.getGathering(gatheringId, userDetails);
        return ResponseEntity.ok(ApiResponse.success(GatheringResultCode.GATHERING_DETAIL_SUCCESS, response));
    }
}
