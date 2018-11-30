package se.haleby.cqrs.lab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CqrsEsLabApplication

fun main(args: Array<String>) {
    runApplication<CqrsEsLabApplication>(*args)
}
