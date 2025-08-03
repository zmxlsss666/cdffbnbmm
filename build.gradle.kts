plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // PF4J插件框架
    implementation("org.pf4j:pf4j:${properties["pf4jVersion"]}")
    
    // JNA - 用于调用Windows API
    implementation("net.java.dev.jna:jna:${properties["jnaVersion"]}")
    implementation("net.java.dev.jna:jna-platform:${properties["jnaVersion"]}")
    
    // JavaFX - 用于UI界面
    implementation("org.openjfx:javafx-base:${properties["javafxVersion"]}")
    implementation("org.openjfx:javafx-controls:${properties["javafxVersion"]}")
    implementation("org.openjfx:javafx-graphics:${properties["javafxVersion"]}")
    implementation("org.openjfx:javafx-swing:${properties["javafxVersion"]}")
    
    // 宿主应用API
    implementation(files("libs/spw-host-api.jar"))
}

tasks {
    // 插件打包任务
    shadowJar {
        archiveBaseName.set("spw-lyric-plugin")
        archiveClassifier.set("")
        manifest {
            attributes(
                "Plugin-Id": "spw-lyric-plugin",
                "Plugin-Class": "com.spw.plugin.LyricPlugin",
                "Plugin-Version": "${project.version}",
                "Plugin-Provider": "SPW Studio",
                "Plugin-Dependencies": ""
            )
        }
    }
    
    // 构建时自动执行打包任务
    build {
        dependsOn(shadowJar)
    }
    
    compileJava {
        options.encoding = "UTF-8"
    }
}
