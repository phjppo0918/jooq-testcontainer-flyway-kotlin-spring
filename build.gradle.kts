import dev.monosoul.jooq.shadow.org.testcontainers.shaded.org.bouncycastle.cms.RecipientId.password
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Strategy

val jooqVersion: String by rootProject

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("dev.monosoul.jooq-docker") version "6.0.26"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    jooqCodegen(project(":jooq-configuration"))
    jooqCodegen("org.jooq:jooq:$jooqVersion")
    jooqCodegen("org.jooq:jooq-meta:$jooqVersion")
    jooqCodegen("org.jooq:jooq-codegen:$jooqVersion")
    jooqCodegen("org.flywaydb:flyway-core:10.8.1")
    jooqCodegen("org.flywaydb:flyway-mysql:10.8.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val dbUser: String = System.getProperty("db-user") ?: "root"
val dbPassword: String = System.getProperty("db-passwd") ?: "verysecret"
val schema: String = System.getProperty("db-schema") ?: "mydatabase"

sourceSets {
    main {
        kotlin {
            srcDirs(listOf("src/main/kotlin", "src/generated"))
        }
    }
}

jooq {

    version = jooqVersion
    withContainer {
        image {
            name = "mysql:8.0.33"
            envVars =
                mapOf(
                    "MYSQL_ROOT_PASSWORD" to dbPassword,
                    "MYSQL_DATABASE" to schema,
                )
        }

        db {
            username = dbUser
            password = dbPassword
            name = schema
            port = 3306
            jdbc {
                schema = "jdbc:mysql"
                driverClassName = "com.mysql.cj.jdbc.Driver"
            }
        }
    }
}

tasks {
    generateJooqClasses {
        schemas.set(listOf(schema))
        outputDirectory.set(project.layout.projectDirectory.dir("src/generated"))
        includeFlywayTable.set(false)

        usingJavaConfig {
            generate =
                Generate()
                    .withJavaTimeTypes(true)
                    .withDeprecated(false)
                    .withDaos(true)
                    .withFluentSetters(true)
                    .withRecords(true)

            withStrategy(
                Strategy().withName("jooq.configuration.generator.JPrefixGeneratorStrategy"),
            )

            database.withForcedTypes(
                ForcedType()
                    .withUserType("java.lang.Long")
                    .withTypes("int unsigned"),
                ForcedType()
                    .withUserType("java.lang.Integer")
                    .withTypes("tinyint unsigned"),
                ForcedType()
                    .withUserType("java.lang.Integer")
                    .withTypes("smallint unsigned"),
            )
        }
    }
}
