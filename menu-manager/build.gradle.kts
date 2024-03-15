@file:Suppress("DEPRECATION")

plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.openapi.generator") version "6.6.0"
}

group = "ru.xzescha.restaurant"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

springBoot {
	mainClass.set("ru.xzescha.restaurant.menumanager.MenuManagerApplication")                      // класс, содержащий main метод для запуска приложения
}


openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("$rootDir/src/main/resources/openapi.yaml")            // путь к спецификации
	outputDir.set("$buildDir/generated")                                 // где генерить код
	apiPackage.set("ru.xzescha.restaurant.menumanager.api")              // пакет для сгенерированных классов API
	modelPackage.set("ru.xzescha.restaurant.menumanager.api.model")      // пакет для моделей
	configOptions.set(mapOf(                                             // доб.ключи позволяющие ограничить то, что будет создано
		"generateApis" to "true",
		"generateSupportingFiles" to "false",
		"interfaceOnly" to "true",
		"library" to "spring-boot",
		"useBeanValidation" to "true",
		"openApiNullable" to "false",
		"skipDefaultInterface" to "true"
	))
}

sourceSets {
	main {
		java {
			srcDirs("$buildDir/generated/src/main/java")        // чтобы можно было писать код, основываясь на автогенеренные классы
		}
	}
}


tasks.compileJava {
	dependsOn("openApiGenerate")                                  // код не скомпилится без автогененеренных классов
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.logging.log4j:log4j-api:2.22.1")          // API логирования
	implementation("org.apache.logging.log4j:log4j-core:2.22.1")         // Ядро логирования с реализацией API
	//implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//testImplementation("org.springframework.security:spring-security-test")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger2:3.0.0")
	implementation("org.flywaydb:flyway-core")
	//implementation("com.h2database:h2:2.2.224")

	implementation("com.github.f4b6a3:uuid-creator:5.3.3")                            // Поддержка UUID v7

	implementation("io.swagger.core.v3:swagger-annotations:2.2.16")

	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("javax.ws.rs:jsr311-api:1.1.1")
	implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("javax.servlet:servlet-api:2.5")
	implementation("org.postgresql:postgresql:42.6.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.1")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
