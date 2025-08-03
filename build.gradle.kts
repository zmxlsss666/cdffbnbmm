plugins {
    `java-library`
    `maven-publish`
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.spw"
version = "1.0.0"
description = "SPW 桌面歌词插件"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing")
}

repositories {
    mavenCentral()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    // PF4J 插件框架
    implementation("org.pf4j:pf4j:3.12.0")
    
    // JNA 用于调用 Windows API
    implementation("net.java.dev.jna:jna:5.13.0")
    implementation("net.java.dev.jna:jna-platform:5.13.0")
    
    // 测试依赖
    testImplementation("junit:junit:4.13.2")
}

tasks.jar {
    // 插件 JAR 文件的名称
    archiveFileName.set("${project.name}-${project.version}.jar")
    
    // 配置 MANIFEST.MF 文件
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Plugin-Id" to "spw-lyric-plugin",
            "Plugin-Class" to "com.spw.plugin.LyricPlugin",
            "Plugin-Version" to "${project.version}",
            "Plugin-Provider" to "SPW Studio",
            "Plugin-Dependencies" to ""
        )
    }
    
    // 处理重复文件策略：保留第一个出现的文件
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    
    // 包含所有依赖
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        // 排除签名文件，避免冲突
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        // 排除可能导致冲突的重复配置文件
        exclude("META-INF/substrate/config/reflectionconfig.json")
    }
}

tasks.test {
    useJUnit()
    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
    
