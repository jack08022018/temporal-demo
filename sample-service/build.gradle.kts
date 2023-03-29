plugins {
    java
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com"
version = "0.0.1"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    all {
        exclude(module = "spring-boot-starter-logging")
    }
}

repositories {
    maven { url = uri("https://repo.spring.io/release") }
    mavenCentral()
}

dependencies {
    implementation ("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springframework.boot:spring-boot-starter-activemq")

    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor ("org.projectlombok:lombok")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
