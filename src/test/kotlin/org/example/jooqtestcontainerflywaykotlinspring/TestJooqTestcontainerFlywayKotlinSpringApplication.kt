package org.example.jooqtestcontainerflywaykotlinspring

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<JooqTestcontainerFlywayKotlinSpringApplication>().with(TestcontainersConfiguration::class)
        .run(*args)
}
