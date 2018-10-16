package com.seanshubin.kotlin.project.generator

import java.nio.file.Path
import java.nio.file.Paths

abstract class Module(val parent: Parent,
                      private val moduleNameParts: List<String>,
                      val dependencies: List<Module>) {
    private val name = moduleNameParts.joinToString("-")
    private val basePath = parent.basePath.resolve(name)
    private val buildPath: Path = basePath.resolve("build.gradle")
    private val implementationName: String = titleCase(moduleNameParts) + "Greeter"
    private val testName: String = implementationName + "Test"
    private val mainName: String = "Main"
    private val sampleImplementationPath = generateSourcePath("main", "$implementationName.kt")
    private val sampleTestPath = generateSourcePath("test", "$testName.kt")
    private val sampleMainPath = generateSourcePath("main", "$mainName.kt")
    private val packageName = (parent.prefixParts + parent.nameParts + moduleNameParts).joinToString(".")
    protected val archivesBaseName = (parent.nameParts + moduleNameParts).joinToString("-")
    val includeLine = "include \":$name\""
    val dependencyLine: String = "    compile project(\":$name\")"

    private fun generateSourcePath(dirName: String, fileName: String): Path {
        val pathParts: List<String> =
                listOf("src", dirName, "kotlin") +
                        parent.prefixParts + parent.nameParts + moduleNameParts + fileName
        val relativePath = Paths.get(pathParts[0], *pathParts.drop(1).toTypedArray())
        return basePath.resolve(relativePath)
    }

    private fun titleCase(parts: List<String>): String = parts.asSequence().map(::capitalize).joinToString("")
    private fun capitalize(s: String): String = s[0].toUpperCase() + s.substring(1)
    protected abstract fun buildFileContent(): List<String>
    protected abstract fun generateFiles()

    fun generate() {
        generateBuild()
        generateFiles()
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(buildPath, buildFileContent())
    }

    protected fun generateImplementation() {
        FileUtil.writeLinesToFile(sampleImplementationPath, generateImplementationLines())
    }

    private fun generateImplementationLines(): List<String> {
        val lines = listOf(
                "package ${packageName}",
                "",
                "class ${implementationName} {",
                "    fun greet(target:String):String = \"Hello, ${'$'}target!\"",
                "}"
        )
        return lines
    }

    protected fun generateTest() {
        FileUtil.writeLinesToFile(sampleTestPath, generateTestLines())
    }

    private fun generateTestLines(): List<String> {
        val lines = listOf(
                "package $packageName",
                "",
                "import kotlin.test.Test",
                "import kotlin.test.assertEquals",
                "",
                "class $testName {",
                "    @Test",
                "    fun greetTest() {",
                "        val greeter = $implementationName()",
                "        assertEquals(\"Hello, world!\", greeter.greet(\"world\"))",
                "    }",
                "}"
        )
        return lines
    }

    protected fun generateMain() {
        FileUtil.writeLinesToFile(sampleMainPath, generateMainLines())
    }

    private fun generateMainLines(): List<String> {
        val lines = listOf(
                "package ${packageName}",
                "",
                "fun main(arguments: Array<String>) {",
                "    val greeter = $implementationName()",
                "    println(greeter.greet(\"world\"))",
                "}"
        )
        return lines
    }
}
