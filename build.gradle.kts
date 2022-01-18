val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation ("io.ktor:ktor-server-netty:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:$logback_version")
    implementation ("io.ktor:ktor-server-core:$ktor_version")
    implementation ("io.ktor:ktor-html-builder:$ktor_version")
    testImplementation ("io.ktor:ktor-server-tests:$ktor_version")
    implementation ("io.ktor:ktor-auth:$ktor_version")
    implementation ("io.ktor:ktor-gson:$ktor_version")
    implementation ("org.litote.kmongo:kmongo:4.4.0")
    implementation ("org.litote.kmongo:kmongo-coroutine:4.4.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation ("commons-codec:commons-codec:1.15")
    implementation ("io.ktor:ktor-network-tls:$ktor_version")

}