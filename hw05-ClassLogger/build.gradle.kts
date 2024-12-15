plugins {
    id("java")
}

group = "org.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

val logback: String by project
val slf4j: String by project

dependencies {
    implementation("ch.qos.logback:logback-classic:${logback}")
    implementation("org.slf4j:slf4j-api:${slf4j}")
}