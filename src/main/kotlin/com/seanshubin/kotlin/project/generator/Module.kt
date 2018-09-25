package com.seanshubin.kotlin.project.generator

import java.nio.file.Path
import java.nio.file.Paths

abstract class Module(val parent:Parent,
             val moduleNameParts: List<String>,
             val dependencies: List<Module>){
    private val name = moduleNameParts.joinToString("-")
    private val basePath = parent.basePath.resolve(name)
    val buildPath: Path = basePath.resolve("build.gradle")
    val implementationName: String = titleCase(moduleNameParts) + "Greeter"
    val testName: String = implementationName + "Test"
    val sampleImplementationPath = generateSourcePath("main", "$implementationName.kt")
    val sampleTestPath = generateSourcePath("test", "$testName.kt")
    val packageName = (parent.prefixParts + parent.nameParts + moduleNameParts).joinToString(".")
    val archivesBaseName = (parent.nameParts + moduleNameParts).joinToString("-")
    val includeLine = "include \":$name\""

    private fun generateSourcePath(dirName: String, fileName: String): Path {
        val pathParts: List<String> =
                listOf("src", dirName, "kotlin") +
                        parent.prefixParts + parent.nameParts + moduleNameParts + fileName
        val relativePath = Paths.get(pathParts[0], *pathParts.drop(1).toTypedArray())
        return basePath.resolve(relativePath)
    }

    private fun titleCase(parts: List<String>): String = parts.asSequence().map(::capitalize).joinToString("")
    private fun capitalize(s: String): String = s[0].toUpperCase() + s.substring(1)
    abstract fun buildFileContent(): List<String>
}
