package rkrk.batch.helper

import org.springframework.jdbc.core.JdbcTemplate
import rkrk.batch.infra.persistence.domain.ReservationStatus
import java.time.LocalDateTime

class InitHelper {
    fun getWareHouseName(): String = "testWareHouse"

    fun getMemberName(): String = "testMember"

    fun jdbcCreate(jdbcTemplate: JdbcTemplate) {
        val insertWareHouseSql =
            "insert into warehouse " +
                " (capacity, created_at, id, minute_price, updated_at, name) " +
                " values ('10', '2023-11-17 21:55:27.000000', '1', '100', '2024-11-17 21:55:27.000000', '${getWareHouseName()}') "
        jdbcTemplate.execute(insertWareHouseSql)
        val mockNow = CustomDateTimeMock().getNow()
        val insertReservationSql1 =
            reservationInsertSqlCreate(
                mockNow.minusMinutes(25),
                LocalDateTime.of(2024, 10, 24, 10, 30),
                LocalDateTime.of(2024, 10, 24, 12, 30),
            )
        val insertReservationSql2 =
            reservationInsertSqlCreate(
                mockNow.minusMinutes(15),
                LocalDateTime.of(2024, 10, 24, 13, 30),
                LocalDateTime.of(2024, 10, 24, 15, 30),
            )
        val insertReservationSql3 =
            reservationInsertSqlCreate(
                mockNow.minusMinutes(5),
                LocalDateTime.of(2024, 10, 25, 13, 30),
                LocalDateTime.of(2024, 10, 25, 15, 30),
            )
        val insertReservationSql4 =
            reservationInsertSqlCreate(
                mockNow.minusMinutes(60),
                LocalDateTime.of(2024, 11, 25, 13, 30),
                LocalDateTime.of(2024, 11, 25, 15, 30),
                ReservationStatus.REFUNDED,
            )
        val insertReservationSql5 =
            reservationInsertSqlCreate(
                mockNow.minusMinutes(60),
                LocalDateTime.of(2024, 11, 25, 13, 30),
                LocalDateTime.of(2024, 11, 25, 15, 30),
                ReservationStatus.CONFIRMED,
            )
        jdbcTemplate.execute(insertReservationSql1)
        jdbcTemplate.execute(insertReservationSql2)
        jdbcTemplate.execute(insertReservationSql3)
        jdbcTemplate.execute(insertReservationSql4)
        jdbcTemplate.execute(insertReservationSql5)
    }

    private fun reservationInsertSqlCreate(
        createTime: LocalDateTime,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        status: ReservationStatus = ReservationStatus.PENDING,
    ): String =
        "insert into reservations (created_at,start_date_time,end_date_time,member_name,state,total_fare,updated_at,warehouse_id) values ('$createTime','$startTime','$endTime','${getMemberName()}','$status',10000,'$createTime',1)"
}
