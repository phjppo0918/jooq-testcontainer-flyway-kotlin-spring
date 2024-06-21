val jooqVersion: String by rootProject

plugins {
    kotlin("jvm")
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq-codegen:$jooqVersion")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
