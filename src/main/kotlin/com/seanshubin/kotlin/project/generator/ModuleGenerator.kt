package com.seanshubin.kotlin.project.generator

import java.nio.file.Path
import java.nio.file.Paths

class ModuleGenerator(private val prefixParts: List<String>,
                      private val nameParts: List<String>,
                      private val moduleNameParts: List<String>,
                      localGithubRoot: Path) {
    private val dirName = nameParts.joinToString("-")
    private val basePath = localGithubRoot.resolve(dirName)

    fun generate() {
        generateBuild()
        generateFiles()
    }

    private fun generateBuild() {
        val relativePath = Paths.get(moduleName(), "build.gradle")
        val path = basePath.resolve(relativePath)
        FileUtil.writeLinesToFile(path, generateBuildContent())
    }

    private fun generateBuildContent(): List<String> {
        return listOf(
                "apply plugin: 'kotlin-platform-common'",
                "",
                "archivesBaseName = 'kotlin-multiplatform-recipes-common'",
                "",
                "dependencies {",
                "    compile libraries.kotlin_stdlib_common",
                "    testCompile libraries.kotlin_test_annotations_common",
                "    testCompile libraries.kotlin_test_common",
                "}",
                "",
                "task sourcesJar(type: Jar) {",
                "    classifier = 'sources'",
                "    from sourceSets.main.kotlin",
                "}",
                "",
                "artifacts {",
                "    archives sourcesJar",
                "}",
                "")
    }

    private fun generateFiles() {
        generateImplementation()
        generateTest()
    }

    private fun generateImplementation() {
        val implementationLines = generateImplementationLines()
        val implementationPath = generateImplementationPath()
        FileUtil.writeLinesToFile(implementationPath, implementationLines)
    }

    private fun generateImplementationPath(): Path = generatePath("main", implementationName())

    private fun generateTestPath(): Path = generatePath("test", testName())

    private fun generatePath(dirName: String, fileName: String): Path {
        val pathParts: List<String> = listOf(moduleName(), "src", dirName) + prefixParts + nameParts + moduleNameParts + fileName
        val relativePath = Paths.get(pathParts[0], *pathParts.drop(1).toTypedArray())
        return basePath.resolve(relativePath)
    }

    private fun moduleName(): String = moduleNameParts.joinToString("-")

    private fun implementationName(): String = titleCase(moduleNameParts) + "Greeter"

    private fun testName(): String = implementationName() + "Test"

    private fun titleCase(parts: List<String>): String = parts.asSequence().map(::capitalize).joinToString("")
    private fun capitalize(s: String): String = s[0].toUpperCase() + s.substring(1)

    private fun generateImplementationLines(): List<String> {
        val packageName = generatePackageName()
        val lines = listOf(
                "package $packageName",
                "",
                "class CommonGreeter {",
                "    fun greet(target:String):String = \"Hello, ${'$'}target!\"",
                "}"
        )
        return lines
    }

    private fun generatePackageName(): String {
        return "package " + (prefixParts + nameParts + moduleNameParts).joinToString(".")
    }

    private fun generateTest() {
        val implementationLines = generateTestLines()
        val implementationPath = generateTestPath()
        FileUtil.writeLinesToFile(implementationPath, implementationLines)
    }

    private fun generateTestLines(): List<String> {
        val packageName = generatePackageName()
        val lines = listOf(
                "package $packageName",
                "",
                "import kotlin.test.Test",
                "import kotlin.test.assertEquals",
                "",
                "class CommonGreeterTest {",
                "    @Test",
                "    fun greetTest() {",
                "        val greeter = CommonGreeter()",
                "        assertEquals(\"Hello, world!\", greeter.greet(\"world\"))",
                "    }",
                "}"
        )
        return lines
    }
}
