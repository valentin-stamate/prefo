plugins {
    id("java")
    kotlin("plugin.lombok") version "2.0.0"
    id("io.freefair.lombok") version "8.1.0"
}

group = "com.valentinstamate"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
