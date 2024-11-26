package rkrk.batch

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rkrk.batch.helper.CustomDateTimeMock
import rkrk.batch.share.CustomDateTime

@Configuration
class TestConfig {
    @Bean
    fun customDateTime(): CustomDateTime = CustomDateTimeMock()
}
