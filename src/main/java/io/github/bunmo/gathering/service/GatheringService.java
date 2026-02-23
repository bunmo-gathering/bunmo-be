package io.github.bunmo.gathering.service;

import io.github.bunmo.common.exception.BusinessException;
import io.github.bunmo.gathering.dto.request.CreateGatheringRequest;
import io.github.bunmo.gathering.dto.response.CreateGatheringResponse;
import io.github.bunmo.gathering.dto.response.GatheringDetailResponse;
import io.github.bunmo.gathering.dto.response.GatheringListPageResponse;
import io.github.bunmo.gathering.dto.response.GatheringListResponse;
import io.github.bunmo.gathering.exception.GatheringErrorCode;
import io.github.bunmo.gathering.infrastructure.domain.Gathering;
import io.github.bunmo.gathering.infrastructure.domain.GatheringDetail;
import io.github.bunmo.gathering.infrastructure.domain.GatheringLocation;
import io.github.bunmo.gathering.infrastructure.domain.GatheringParticipant;
import io.github.bunmo.gathering.infrastructure.domain.enums.ActiveType;
import io.github.bunmo.gathering.infrastructure.domain.enums.GatheringType;
import io.github.bunmo.gathering.infrastructure.repository.GatheringRepository;
import io.github.bunmo.member.infrastructure.domain.Member;
import io.github.bunmo.member.infrastructure.repository.MemberRepository;
import io.github.bunmo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateGatheringResponse createGathering(CreateGatheringRequest request, CustomUserDetails userDetails) {
        if (request.type() == GatheringType.ONLINE && (request.productLink() == null || request.productLink().isBlank())) {
            throw new BusinessException(GatheringErrorCode.PRODUCT_LINK_REQUIRED);
        }

        Member member = memberRepository.findByUuid(userDetails.getUuid())
                .orElseThrow(() -> new BusinessException(GatheringErrorCode.GATHERING_NOT_FOUND));
        Long currentMemberId = member.getId();

        GatheringDetail detail = new GatheringDetail(
                request.title(),
                request.description(),
                request.openChatLink()
        );

        // TODO: 추후 클라이언트에서 위도/경도를 넘겨주도록 변경 시 이 부분 수정 필요
        GatheringLocation location = new GatheringLocation(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                request.location()
        );

        Gathering gathering = Gathering.create(
                currentMemberId, // DB에서 찾아오던 부분 대체
                request.type(),
                detail,
                request.category(),
                request.maxParticipants(),
                location,
                request.price(),
                request.productLink(),
                request.meetingDate(),
                request.meetingTime()
        );

        return CreateGatheringResponse.from(gatheringRepository.save(gathering));
    }

    public GatheringListPageResponse getGatherings(int page, int size, LocalDate date) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<GatheringListResponse> result = gatheringRepository
                .findAllByActiveTypeAndDate(ActiveType.ACTIVE, date, pageable)
                .map(GatheringListResponse::from);
        return GatheringListPageResponse.from(result);
    }

    public GatheringDetailResponse getGathering(Long gatheringId, CustomUserDetails userDetails) {
        Gathering gathering = gatheringRepository.findByIdAndActiveType(gatheringId, ActiveType.ACTIVE)
                .orElseThrow(() -> new BusinessException(GatheringErrorCode.GATHERING_NOT_FOUND));

        // 💡 개선포인트 2: 에러 코드를 분리할 수 있다면 MemberErrorCode.MEMBER_NOT_FOUND 등으로 수정 권장
        Member host = memberRepository.findById(gathering.getOwnerId())
                .orElseThrow(() -> new BusinessException(GatheringErrorCode.GATHERING_NOT_FOUND));

        List<Long> participantMemberIds = gathering.getParticipants().stream()
                .map(GatheringParticipant::getMemberId)
                .toList();

        // 💡 개선포인트 3: 참여자가 0명일 때 쓸데없는 DB 조회를 막는 방어 로직 추가
        List<Member> participantMembers = Collections.emptyList();
        if (!participantMemberIds.isEmpty()) {
            Map<Long, Member> participantMemberMap = memberRepository.findAllById(participantMemberIds).stream()
                    .collect(Collectors.toMap(Member::getId, Function.identity()));

            participantMembers = participantMemberIds.stream()
                    .map(participantMemberMap::get)
                    .filter(Objects::nonNull)
                    .toList();
        }

        Member currentMember = memberRepository.findByUuid(userDetails.getUuid())
                .orElseThrow(() -> new BusinessException(GatheringErrorCode.GATHERING_NOT_FOUND));
        Long currentMemberId = currentMember.getId();

        return GatheringDetailResponse.of(gathering, host, participantMembers, currentMemberId);
    }
}