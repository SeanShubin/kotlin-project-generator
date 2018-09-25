package com.seanshubin.kotlin.project.generator

class ParentGenerator(private val names:Names,
                      private val moduleNames: List<List<String>>) {

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
        val moduleNames = names.moduleNames(moduleNameParts)
        return ModuleGenerator(moduleNames)
    }

    private fun generateSettings() {
        FileUtil.writeLinesToFile(names.settingsPath, settingsContent())
    }

    private fun settingsContent(): List<String> {
        val root = "rootProject.name = \"${names.name}\""
        val includeLines = moduleNames.map(::generateIncludeLine)
        return listOf(root, "") + includeLines
    }

    private fun generateIncludeLine(moduleNameParts: List<String>): String {
        val moduleName = moduleNameParts.joinToString("-")
        return "include \":$moduleName\""
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(names.buildPath, generateBuildContent())
    }

    private fun generateBuildContent():List<String> {
        return listOf(
                "allprojects {",
                "    group \"${names.group}\"",
                "    version \"1.0.0-SNAPSHOT\"",
                "}",
                "",
                "wrapper {",
                "  gradleVersion = \"4.9\"",
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
