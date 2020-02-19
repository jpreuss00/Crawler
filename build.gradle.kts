/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.1.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:28.1-jre")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
    
    implementation("org.postgresql:postgresql:42.2.10")
}

application {
    // Define the main class for the application.
    mainClassName = "src.main.java.crawler.Crawler"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf("--release", "8"))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "src.main.java.crawler.Crawler"
    }

	from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
    	configurations.runtimeClasspath.get().filter {
    		it.name.endsWith("jar") }.map { zipTree(it) }
	})
}
