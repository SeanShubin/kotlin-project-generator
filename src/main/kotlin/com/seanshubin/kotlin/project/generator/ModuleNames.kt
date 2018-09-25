package com.seanshubin.kotlin.project.generator

import java.nio.file.Path
import java.nio.file.Paths

class ModuleNames(private val names: Names,
                  private val module: Module) {
    private val moduleNameParts:List<String> = module.nameParts
    private val name = moduleNameParts.joinToString("-")
    private val basePath = names.basePath.resolve(name)
    val buildPath: Path = basePath.resolve("build.gradle")
    val implementationName: String = titleCase(moduleNameParts) + "Greeter"
    val testName: String = implementationName + "Test"
    val sampleImplementationPath = generateSourcePath("main", "$implementationName.kt")
    val sampleTestPath = generateSourcePath("test", "$testName.kt")
    val packageName = (names.prefixParts + names.nameParts + moduleNameParts).joinToString(".")
    val archivesBaseName = (names.nameParts + moduleNameParts).joinToString("-")
    private fun generateSourcePath(dirName: String, fileName: String): Path {
        val pathParts: List<String> =
                listOf("src", dirName, "kotlin") +
                        names.prefixParts + names.nameParts + moduleNameParts + fileName
        val relativePath = Paths.get(pathParts[0], *pathParts.drop(1).toTypedArray())
        return basePath.resolve(relativePath)
    }

    private fun titleCase(parts: List<String>): String = parts.asSequence().map(::capitalize).joinToString("")
    private fun capitalize(s: String): String = s[0].toUpperCase() + s.substring(1)
}
