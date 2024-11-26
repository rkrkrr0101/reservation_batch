package rkrk.batch.infra.persistence

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import rkrk.batch.infra.persistence.entity.ReservationJpaEntity
import java.time.LocalDateTime

interface ReservationJpaRepository : JpaRepository<ReservationJpaEntity, Long> {
    @Query(
        " select r from ReservationJpaEntity r " +
            " where r.createdAt<:thresholdTime " +
            " and r.state= 'PENDING'  ",
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")])
    fun findAndLockByCreateTimeAndPending(thresholdTime: LocalDateTime): List<ReservationJpaEntity>
}
