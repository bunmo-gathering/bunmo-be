package io.github.bunmo.gathering.infrastructure.repository;

import io.github.bunmo.gathering.infrastructure.domain.Gathering;
import io.github.bunmo.gathering.infrastructure.domain.enums.ActiveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    @Query("SELECT g FROM Gathering g WHERE g.activeType = :activeType AND (:date IS NULL OR g.meetingDate = :date)")
    Page<Gathering> findAllByActiveTypeAndDate(
            @Param("activeType") ActiveType activeType,
            @Param("date") LocalDate date,
            Pageable pageable
    );

    @Query("SELECT g FROM Gathering g LEFT JOIN FETCH g.participants WHERE g.id = :id AND g.activeType = :activeType")
    Optional<Gathering> findByIdAndActiveType(
            @Param("id") Long id,
            @Param("activeType") ActiveType activeType
    );
}
