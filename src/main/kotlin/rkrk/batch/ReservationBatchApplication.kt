package rkrk.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ReservationBatchApplication

fun main(args: Array<String>) {
    runApplication<ReservationBatchApplication>(*args)
}
