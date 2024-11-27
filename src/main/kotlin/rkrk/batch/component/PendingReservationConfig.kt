package rkrk.batch.component

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import rkrk.batch.infra.persistence.entity.ReservationJpaEntity
import rkrk.batch.share.CustomDateTime

@Configuration
class PendingReservationConfig(
    private val emFactory: EntityManagerFactory,
    private val customDateTime: CustomDateTime,
) {
    companion object {
        const val JOB_NAME = "pendingUpdateJob"
    }

    private val chunkSize = 10

    @Bean
    fun pendingUpdateJob(
        jobRepository: JobRepository,
        pendingUpdateStep: Step,
    ): Job =
        JobBuilder(JOB_NAME, jobRepository)
            .incrementer(RunIdIncrementer())
            .start(pendingUpdateStep)
            .build()

    @JobScope
    @Bean
    fun pendingUpdateStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        pendingReservationReader: JpaPagingItemReader<ReservationJpaEntity>,
        pendingReservationCancelProcessor: PendingReservationCancelProcessor,
        jpaWriter: JpaItemWriter<ReservationJpaEntity>,
        stepErrorLoggingListener: StepErrorLoggingListener,
    ): Step =
        StepBuilder("pendingUpdateStep", jobRepository)
            .chunk<ReservationJpaEntity, ReservationJpaEntity>(chunkSize, transactionManager)
            .reader(pendingReservationReader)
            .processor(pendingReservationCancelProcessor)
            .writer(jpaWriter)
            .listener(stepErrorLoggingListener)
            .build()

    @Bean
    @StepScope
    fun pendingReservationReader(): JpaPagingItemReader<ReservationJpaEntity> {
        val reader: JpaPagingItemReader<ReservationJpaEntity> =
            object : JpaPagingItemReader<ReservationJpaEntity>() {
                override fun getPage(): Int = 0
            }
        val queryProvider =
            JpaNativeQueryProvider<ReservationJpaEntity>().apply {
                setSqlQuery(
                    " select * from reservations r " +
                        " where r.created_at<:thresholdTime " +
                        " and r.state= 'PENDING'  " +
                        " for update ",
                )
                setEntityClass(ReservationJpaEntity::class.java)
            }

        reader.name = "jpaPagingItemReader"
        reader.setEntityManagerFactory(emFactory)
        reader.pageSize = chunkSize
        reader.setQueryProvider(queryProvider)
        reader.setParameterValues(mapOf("thresholdTime" to customDateTime.getNow().minusMinutes(20)))

        return reader
    }

    @Bean
    @StepScope
    fun pendingReservationCancelProcessor(): PendingReservationCancelProcessor = PendingReservationCancelProcessor()

    @Bean
    @StepScope
    fun jpaWriter(): JpaItemWriter<ReservationJpaEntity> {
        val writer = JpaItemWriter<ReservationJpaEntity>()
        writer.setEntityManagerFactory(emFactory)

        return writer
    }
}
