package com.seanshubin.kotlin.project.generator

object DependencyInjection {
    private val homeEnv = System.getenv("HOME")
    private val prefixParts = listOf("com", "seanshubin")
    private val nameParts = listOf("kotlin", "generated", "multiplatform")
    private val githubRelative = listOf("github", "sean")
    private val namesAndPaths: Names = Names(homeEnv, prefixParts, nameParts, githubRelative)
    private val genericCommon = Module(listOf("common", "generic"), CommonModule, emptyList())
    private val frontEndCommon = Module(listOf("common", "frontend"), CommonModule, listOf(genericCommon))
    private val backEndCommon = Module(listOf("common", "backend"), CommonModule, listOf(genericCommon))
    private val frontEndPlatform = Module(listOf("js", "frontend"), JsModule, listOf(frontEndCommon))
    private val backEndPlatform = Module(listOf("jvm", "backend"), CommonModule, listOf(backEndCommon))
    private val frontEndEntry = Module(listOf("js", "app"), CommonModule, listOf(frontEndPlatform))
    private val backEndEntry = Module(listOf("jvm", "app"), CommonModule, listOf(backEndPlatform))
    private val modules: List<Module> = listOf(
            frontEndEntry,
            backEndEntry,
            frontEndPlatform,
            backEndPlatform,
            frontEndCommon,
            backEndCommon,
            genericCommon
    )
    private val parentGenerator: ParentGenerator = ParentGenerator(namesAndPaths, modules)
    val application: Runnable = ProjectGenerator(parentGenerator)
}
