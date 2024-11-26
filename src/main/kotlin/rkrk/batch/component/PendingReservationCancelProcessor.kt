package rkrk.batch.component

import org.springframework.batch.item.ItemProcessor
import rkrk.batch.infra.persistence.domain.ReservationStatus
import rkrk.batch.infra.persistence.entity.ReservationJpaEntity

open class PendingReservationCancelProcessor : ItemProcessor<ReservationJpaEntity, ReservationJpaEntity> {
    override fun process(item: ReservationJpaEntity): ReservationJpaEntity {
        if (item.state == ReservationStatus.PENDING) {
            item.updateState(ReservationStatus.CANCELLED)
        }
        return item
    }
}
