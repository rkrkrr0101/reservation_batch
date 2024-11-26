package rkrk.batch.infra.persistence

import org.springframework.stereotype.Repository

@Repository
class ReservationRepository(
    private val repository: ReservationJpaRepository,
)
