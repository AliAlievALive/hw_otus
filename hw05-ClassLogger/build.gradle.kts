plugins {
    id("java")
}

group = "org.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation("org.slf4j:slf4j-api")
}