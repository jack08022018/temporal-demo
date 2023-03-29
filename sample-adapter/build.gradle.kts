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
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-netty-http-starter:3.20.1")
	implementation("org.apache.camel.springboot:camel-jackson-starter:3.20.1")
	implementation("org.apache.camel:camel-jms:3.20.1")
	implementation("org.springframework.boot:spring-boot-starter-activemq")
	implementation("com.ibm.mq:mq-jms-spring-boot-starter:2.6.5")
	implementation("org.jboss.narayana.jta:narayana-jta:5.5.32.Final")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:2.7.3")
	implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.3")

}
tasks.withType<Test> {
	useJUnitPlatform()
}