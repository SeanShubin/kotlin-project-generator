package com.seanshubin.kotlin.project.generator

import java.nio.charset.Charset
import java.nio.file.Path

class ParentGenerator(private val prefixParts:List<String>,
                      private val nameParts: List<String>,
                      private val moduleNames: List<List<String>>,
                      private val localGithubRoot: Path) {
    private val dirName = nameParts.joinToString("-")
    private val basePath = localGithubRoot.resolve(dirName)

    fun generate() {
        generateSettings()
        generateBuild()
        moduleNames.forEach(::generateModule)
    }

    private fun generateModule(moduleNameParts:List<String>){
        val moduleGenerator = createModuleGenerator(moduleNameParts)
        moduleGenerator.generate()
    }

    private fun createModuleGenerator(moduleNameParts:List<String>):ModuleGenerator {
        return ModuleGenerator(prefixParts, nameParts, moduleNameParts, localGithubRoot)
    }

    private fun generateSettings() {
        generateFile("settings.gradle", settingsContent())
    }

    private fun settingsContent(): List<String> {
        val name = nameParts.joinToString("-")
        val root = "rootProject.name = '$name'"
        val includeLines = moduleNames.map(::generateIncludeLine)
        return listOf(root, "") + includeLines
    }

    private fun generateIncludeLine(moduleNameParts: List<String>): String {
        val moduleName = moduleNameParts.joinToString("-")
        return "include ':$moduleName'"
    }

    private fun generateFile(name: String, lines: List<String>) {
        val path = basePath.resolve(name)
        FileUtil.writeLinesToFile(path, lines)
    }

    private fun generateBuild() {
        generateFile("build.gradle", generateBuildContent())
    }

    private fun generateBuildContent():List<String> {
        return listOf(
                "allprojects {",
                "    group 'com.javathinker.research'",
                "    version '1-SNAPSHOT'",
                "}",
                "",
                "wrapper {",
                "  gradleVersion = '4.9'",
                "  distributionUrl = 'https://services.gradle.org/distributions/gradle-${'$'}gradleVersion-all.zip'",
                "}",
                "",
                "buildscript {",
                "    ext.kotlinVersion = '1.2.60'",
                "",
                "    repositories {",
                "        jcenter()",
                "        mavenCentral()",
                "        maven { url 'https://plugins.gradle.org/m2/' }",
                "    }",
                "    dependencies {",
                "        classpath 'com.eriwen:gradle-js-plugin:2.14.1'",
                "        classpath 'com.moowork.gradle:gradle-node-plugin:1.2.0'",
                "        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:${'$'}kotlinVersion'",
                "    }",
                "}",
                "",
                "ext {",
                "    nodeVersion = '8.11.3'",
                "    qunitVersion = '2.6.1'",
                "    libraries = [",
                "            junit: 'junit:junit:4.12',",
                "            kotlin_stdlib: 'org.jetbrains.kotlin:kotlin-stdlib:${'$'}kotlinVersion',",
                "            kotlin_stdlib_common: 'org.jetbrains.kotlin:kotlin-stdlib-common:${'$'}kotlinVersion',",
                "            kotlin_stdlib_js: 'org.jetbrains.kotlin:kotlin-stdlib-js:${'$'}kotlinVersion',",
                "            kotlin_test_annotations_common: 'org.jetbrains.kotlin:kotlin-test-annotations-common:${'$'}kotlinVersion',",
                "            kotlin_test_common: 'org.jetbrains.kotlin:kotlin-test-common:${'$'}kotlinVersion',",
                "            kotlin_test_js: 'org.jetbrains.kotlin:kotlin-test-js:${'$'}kotlinVersion',",
                "            kotlin_test_junit: 'org.jetbrains.kotlin:kotlin-test-junit:${'$'}kotlinVersion',",
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
