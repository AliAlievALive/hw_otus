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
val assertj: String by project

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback")
    implementation("org.slf4j:slf4j-api:$slf4j")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.assertj:assertj-core:$assertj")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}