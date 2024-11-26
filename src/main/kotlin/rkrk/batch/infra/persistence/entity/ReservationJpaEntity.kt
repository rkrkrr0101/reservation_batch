package rkrk.batch.infra.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import rkrk.batch.infra.persistence.domain.ReservationStatus
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
class ReservationJpaEntity(
    @Column(nullable = false)
    var memberName: String,
    @Column(nullable = false)
    var startDateTime: LocalDateTime,
    @Column(nullable = false)
    var endDateTime: LocalDateTime,
    var totalFare: BigDecimal,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var state: ReservationStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    var wareHouse: WareHouseJpaEntity,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Suppress("unused")
    var id: Long = 0,
) : BaseEntity() {
    fun updateState(reservationStatus: ReservationStatus) {
        this.state = reservationStatus
    }
}
