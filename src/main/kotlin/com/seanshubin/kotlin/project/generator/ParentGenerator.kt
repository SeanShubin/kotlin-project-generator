package com.seanshubin.kotlin.project.generator

class ParentGenerator(private val parent: Parent,
                      private val modules: List<Module>) {

    fun generate() {
        generateSettings()
        generateBuild()
        modules.forEach { it.generate() }
    }

    private fun generateSettings() {
        FileUtil.writeLinesToFile(parent.settingsPath, settingsContent())
    }

    private fun settingsContent(): List<String> {
        val root = "rootProject.name = \"${parent.name}\""
        val includeLines = modules.map { it.includeLine }
        return listOf(root, "") + includeLines
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(parent.buildPath, generateBuildContent())
    }

    private fun generateBuildContent(): List<String> {
        return listOf(
                "allprojects {",
                "    group \"${parent.group}\"",
                "    version \"1.0.0-SNAPSHOT\"",
                "}",
                "",
                "wrapper {",
                "  gradleVersion = \"4.10.2\"",
                "  distributionUrl = \"https://services.gradle.org/distributions/gradle-${'$'}gradleVersion-all.zip\"",
                "}",
                "",
                "buildscript {",
                "    ext.kotlinVersion = \"1.2.60\"",
                "",
                "    repositories {",
                "        jcenter()",
                "        mavenCentral()",
                "        maven { url \"https://plugins.gradle.org/m2/\" }",
                "    }",
                "    dependencies {",
                "        classpath \"com.eriwen:gradle-js-plugin:2.14.1\"",
                "        classpath \"com.moowork.gradle:gradle-node-plugin:1.2.0\"",
                "        classpath \"org.jetbrains.kotlin:kotlin-gradle-plugin:${'$'}kotlinVersion\"",
                "    }",
                "}",
                "",
                "ext {",
                "    nodeVersion = \"8.11.3\"",
                "    qunitVersion = \"2.6.1\"",
                "    libraries = [",
                "            junit: \"junit:junit:4.12\",",
                "            kotlin_stdlib: \"org.jetbrains.kotlin:kotlin-stdlib:${'$'}kotlinVersion\",",
                "            kotlin_stdlib_common: \"org.jetbrains.kotlin:kotlin-stdlib-common:${'$'}kotlinVersion\",",
                "            kotlin_stdlib_js: \"org.jetbrains.kotlin:kotlin-stdlib-js:${'$'}kotlinVersion\",",
                "            kotlin_test_annotations_common: \"org.jetbrains.kotlin:kotlin-test-annotations-common:${'$'}kotlinVersion\",",
                "            kotlin_test_common: \"org.jetbrains.kotlin:kotlin-test-common:${'$'}kotlinVersion\",",
                "            kotlin_test_js: \"org.jetbrains.kotlin:kotlin-test-js:${'$'}kotlinVersion\",",
                "            kotlin_test_junit: \"org.jetbrains.kotlin:kotlin-test-junit:${'$'}kotlinVersion\",",
                "    ]",
                "}",
                "",
                "subprojects {",
                "    repositories {",
                "        jcenter()",
                "        mavenCentral()",
                "    }",
                "}",
                "")

    }
}
