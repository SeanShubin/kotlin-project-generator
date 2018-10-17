package com.seanshubin.kotlin.project.generator

class ParentGenerator(private val parent: Parent,
                      private val modules: List<Module>,
                      private val versions:Versions) {

    fun generate() {
        generateSettings()
        generateBuild()
        generateGitIgnore()
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
                "    version \"${versions.self}\"",
                "}",
                "",
                "wrapper {",
                "  gradleVersion = \"${versions.gradle}\"",
                "  distributionUrl = \"https://services.gradle.org/distributions/gradle-${'$'}gradleVersion-all.zip\"",
                "}",
                "",
                "buildscript {",
                "    ext.kotlinVersion = \"${versions.kotlin}\"",
                "",
                "    repositories {",
                "        jcenter()",
                "        mavenCentral()",
                "        maven { url \"https://plugins.gradle.org/m2/\" }",
                "    }",
                "    dependencies {",
                "        classpath \"com.eriwen:gradle-js-plugin:${versions.gradleJsPlugin}\"",
                "        classpath \"com.moowork.gradle:gradle-node-plugin:${versions.gradleNodePlugin}\"",
                "        classpath \"org.jetbrains.kotlin:kotlin-gradle-plugin:${'$'}kotlinVersion\"",
                "    }",
                "}",
                "",
                "ext {",
                "    nodeVersion = \"${versions.node}\"",
                "    qunitVersion = \"${versions.qunit}\"",
                "    libraries = [",
                "            junit: \"junit:junit:${versions.junit}\",",
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

    private fun generateGitIgnore() {
        val lines = listOf(
                "**/.gradle/",
                "**/build/",
                "**/node_modules/",
                ".idea/",
                "**/out/",
                "*.iml")
        FileUtil.writeLinesToFile(parent.gitIgnorePath, lines)
    }
}
