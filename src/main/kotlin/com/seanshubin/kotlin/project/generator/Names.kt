package com.seanshubin.kotlin.project.generator

import java.nio.file.Paths

class Names(homeEnv:String,
            val prefixParts:List<String>,
            val nameParts:List<String>,
            githubRelative:List<String>) {
    val name = nameParts.joinToString("-")
    val group = (prefixParts + nameParts).joinToString(".")
    private val localGithubRoot = Paths.get(homeEnv, *githubRelative.toTypedArray())
    val basePath = localGithubRoot.resolve(name)
    val settingsPath = basePath.resolve("settings.gradle")
    val buildPath = basePath.resolve("build.gradle")
    fun moduleNames(moduleNameParts:List<String>):ModuleNames = ModuleNames(this, moduleNameParts)
}
