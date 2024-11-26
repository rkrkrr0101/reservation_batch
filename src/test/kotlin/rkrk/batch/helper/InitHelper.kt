package rkrk.batch.helper

import rkrk.batch.infra.persistence.ReservationJpaRepository
import rkrk.batch.infra.persistence.WareHouseJpaRepository
import rkrk.batch.infra.persistence.domain.ReservationStatus
import rkrk.batch.infra.persistence.entity.ReservationJpaEntity
import rkrk.batch.infra.persistence.entity.WareHouseJpaEntity
import java.math.BigDecimal
import java.time.LocalDateTime

class InitHelper {
    fun getWareHouseName(): String = "testWareHouse"

    fun getMemberName(): String = "testMember"

    fun basicInit(
        wareHouseJpaRepository: WareHouseJpaRepository,
        reservationJpaRepository: ReservationJpaRepository,
    ) {
        val wareHouseEntity =
            WareHouseJpaEntity(
                getWareHouseName(),
                1000,
                100,
                mutableListOf(),
            )
        val reservationEntity1 =
            ReservationJpaEntity(
                getMemberName(),
                LocalDateTime.of(2024, 10, 24, 10, 30),
                LocalDateTime.of(2024, 10, 24, 12, 30),
                BigDecimal("1000"),
                ReservationStatus.PENDING,
                wareHouseEntity,
            )
        val reservationEntity2 =
            ReservationJpaEntity(
                getMemberName(),
                LocalDateTime.of(2024, 10, 24, 13, 30),
                LocalDateTime.of(2024, 10, 24, 15, 30),
                BigDecimal("1000"),
                ReservationStatus.PENDING,
                wareHouseEntity,
            )
        val reservationEntity3 =
            ReservationJpaEntity(
                getMemberName(),
                LocalDateTime.of(2024, 10, 25, 13, 30),
                LocalDateTime.of(2024, 10, 25, 15, 30),
                BigDecimal("1000"),
                ReservationStatus.PENDING,
                wareHouseEntity,
            )
        reservationEntity1.createdAt = CustomDateTimeMock().getNow().minusMinutes(90)
        reservationEntity1.updatedAt = CustomDateTimeMock().getNow().minusMinutes(90)
        reservationEntity2.createdAt = CustomDateTimeMock().getNow().minusMinutes(20)
        reservationEntity2.updatedAt = CustomDateTimeMock().getNow().minusMinutes(20)
        reservationEntity3.createdAt = CustomDateTimeMock().getNow().minusMinutes(10)
        reservationEntity3.updatedAt = CustomDateTimeMock().getNow().minusMinutes(10)
        wareHouseJpaRepository.save(wareHouseEntity)
        reservationJpaRepository.save(reservationEntity1)
        reservationJpaRepository.save(reservationEntity2)
        reservationJpaRepository.save(reservationEntity3)
    }

    fun basicClear(
        wareHouseJpaRepository: WareHouseJpaRepository,
        reservationJpaRepository: ReservationJpaRepository,
    ) {
        reservationJpaRepository.deleteAll()
        wareHouseJpaRepository.deleteAll()
    }
}
