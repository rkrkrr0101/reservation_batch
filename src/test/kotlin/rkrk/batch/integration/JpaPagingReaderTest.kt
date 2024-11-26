package rkrk.batch.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import rkrk.batch.component.PendingReservationConfig
import rkrk.batch.helper.InitHelper
import rkrk.batch.infra.persistence.ReservationJpaRepository
import rkrk.batch.infra.persistence.WareHouseJpaRepository
import rkrk.reservation.helper.SpringTestContainerTest

@SpringTestContainerTest
@SpringBatchTest
@TestPropertySource(properties = ["job.name=" + PendingReservationConfig.JOB_NAME ])
class JpaPagingReaderTest(
    @Autowired private val wareHouseJpaRepository: WareHouseJpaRepository,
    @Autowired private val reservationJpaRepository: ReservationJpaRepository,
    @Autowired private val jobLauncherTestUtils: JobLauncherTestUtils,
) {
    private val initHelper = InitHelper()

    @Test
    @DisplayName("aa")
    fun abcd() {
        initHelper.basicInit(wareHouseJpaRepository, reservationJpaRepository)
        // jpaPagingItemReader.set
        reservationJpaRepository.findAll().forEach { println(it.state) }

        val jobExecution = jobLauncherTestUtils.launchJob()

        Assertions.assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)

        reservationJpaRepository.findAll().forEach { println(it.state) }
        reservationJpaRepository.findAll().forEach { println(it.createdAt) }
    }
}
