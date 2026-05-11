plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.mordant)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertk)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "me.dmadouros.pokerhand.application.AppKt"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
