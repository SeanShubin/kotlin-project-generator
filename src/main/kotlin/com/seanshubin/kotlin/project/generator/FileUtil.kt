package com.seanshubin.kotlin.project.generator

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object FileUtil {
    private val charset = StandardCharsets.UTF_8
    fun writeLinesToFile(path: Path, lines: List<String>) {
        println("Writing ${lines.size} lines to file $path")
        Files.createDirectories(path.parent)
        Files.write(path, lines, charset)
    }
}
