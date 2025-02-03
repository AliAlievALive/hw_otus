plugins {
    id("java")
}

group = "org.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.projectlombok:lombok")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("com.zaxxer:HikariCP")

    annotationProcessor ("org.projectlombok:lombok")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.test {
    useJUnitPlatform()
}