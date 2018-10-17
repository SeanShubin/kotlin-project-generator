package com.seanshubin.kotlin.project.generator

import java.nio.file.Path
import java.nio.file.Paths

class Parent(homeEnv: String,
             val prefixParts: List<String>,
             val nameParts: List<String>,
             githubRelative: List<String>) {
    val name = nameParts.joinToString("-")
    val group = (prefixParts + nameParts).joinToString(".")
    private val localGithubRoot = Paths.get(homeEnv, *githubRelative.toTypedArray())
    val basePath: Path = localGithubRoot.resolve(name)
    val settingsPath: Path = basePath.resolve("settings.gradle")
    val buildPath: Path = basePath.resolve("build.gradle")
    val gitIgnorePath:Path = basePath.resolve(".gitignore")
}
