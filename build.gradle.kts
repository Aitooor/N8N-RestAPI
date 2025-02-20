import java.nio.file.Files
import java.nio.file.Paths

plugins{
	id("java")
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.ignisnw"
version = "0.0.3"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val snippetsDir = file("build/generated-snippets") // Definir snippetsDir aquí

extra["snippetsDir"] = snippetsDir

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springframework.retry:spring-retry")

	//Telegram
	implementation("org.telegram:telegrambots-spring-boot-starter:6.0.1")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

// Custom Build Tasks

tasks.register("cleanLocalServerJar") {
	doLast {
		val localServerDir = Paths.get("localhost")
		Files.newDirectoryStream(localServerDir, "*.jar").use { stream ->
			stream.forEach { Files.delete(it) }
		}

		val gradleVersion = project.version
		val scriptFile = localServerDir.resolve("start.sh")
		val scriptLines = Files.readAllLines(scriptFile)
		val updatedScriptLines = scriptLines.map { line ->
			if (line.startsWith("VERSION=")) {
				"VERSION=\"$gradleVersion\""
			} else {
				line
			}
		}
		Files.write(scriptFile, updatedScriptLines)
	}
}

tasks.register("copyJarToLocalServer") {
	dependsOn("cleanLocalServerJar")
	doLast {
		val jarFile = tasks.bootJar.get().archiveFile.get().asFile
		val localServerDir = file("localhost")
		copy {
			from(jarFile)
			into(localServerDir)
		}
	}
}

tasks.register("buildAndCopyJarToLocalServer") {
	dependsOn("build", "copyJarToLocalServer")
}

// Test Tasks

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName("test") {
	outputs.dir(snippetsDir)
}

tasks.getByName("asciidoctor") {
	inputs.dir(snippetsDir)
	dependsOn(tasks.getByName("test"))
}