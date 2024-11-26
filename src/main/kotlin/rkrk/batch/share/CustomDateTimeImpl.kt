package rkrk.batch.share

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomDateTimeImpl : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.now()
}
