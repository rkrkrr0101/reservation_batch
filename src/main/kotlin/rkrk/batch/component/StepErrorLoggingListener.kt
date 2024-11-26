package rkrk.batch.component

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecutionListener
import org.springframework.stereotype.Component

@Component
class StepErrorLoggingListener : StepExecutionListener {
    override fun afterStep(stepExecution: org.springframework.batch.core.StepExecution): ExitStatus? {
        if (stepExecution.failureExceptions.isNotEmpty()) {
            stepExecution.failureExceptions.forEach { exception ->
                println("예외 발생: ${exception.message}")
            }
        }
        return stepExecution.exitStatus
    }
}
