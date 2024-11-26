package rkrk.batch.helper

import rkrk.batch.share.CustomDateTime
import java.time.LocalDateTime

class CustomDateTimeMock : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.of(2024, 9, 23, 13, 30)
}
