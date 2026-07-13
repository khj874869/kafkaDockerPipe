plugins {
  id("java")
  id("org.springframework.boot") version "3.3.2"
  id("io.spring.dependency-management") version "1.1.5"
}
group = "com.portfolio"; version = "0.0.1"
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }
repositories { mavenCentral() }
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-database-postgresql")
  runtimeOnly("org.postgresql:postgresql")
  implementation("io.micrometer:micrometer-registry-prometheus")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
tasks.withType<org.gradle.api.tasks.testing.Test>().configureEach { useJUnitPlatform() }
