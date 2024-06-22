package org.example.jooqtestcontainerflywaykotlinspring

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    fun mysqlContainer(): MySQLContainer<*> {
        val container = MySQLContainer(DockerImageName.parse("mysql:latest"))
        container.withDatabaseName("mydatabase")
        container.withUsername("root")
        container.withPassword("verysecret")
        return container
    }
}
