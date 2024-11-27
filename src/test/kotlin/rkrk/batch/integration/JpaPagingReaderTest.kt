package rkrk.batch.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestPropertySource
import rkrk.batch.component.PendingReservationConfig
import rkrk.batch.helper.InitHelper
import rkrk.batch.infra.persistence.ReservationJpaRepository
import rkrk.batch.infra.persistence.domain.ReservationStatus
import rkrk.reservation.helper.SpringTestContainerTest

@SpringTestContainerTest
@SpringBatchTest
@TestPropertySource(properties = ["job.name=" + PendingReservationConfig.JOB_NAME ])
class JpaPagingReaderTest(
    @Autowired private val reservationJpaRepository: ReservationJpaRepository,
    @Autowired private val jobLauncherTestUtils: JobLauncherTestUtils,
    @Autowired private val jdbcTemplate: JdbcTemplate,
) {
    private val initHelper = InitHelper()

    @Test
    @DisplayName("잡을 실행시켜서 20분이 지난 임시예약을 취소시킬수있다")
    fun cancelPendingReservation() {
        initHelper.jdbcCreate(jdbcTemplate)

        val jobExecution = jobLauncherTestUtils.launchJob()

        val reservationJpaEntities = reservationJpaRepository.findAll()

        Assertions.assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)
        Assertions
            .assertThat(
                reservationJpaEntities.filter { it.state == ReservationStatus.PENDING }.size,
            ).isEqualTo(2)
        Assertions
            .assertThat(
                reservationJpaEntities.filter { it.state == ReservationStatus.CANCELLED }.size,
            ).isEqualTo(1)
    }
}
