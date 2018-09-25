package com.seanshubin.kotlin.project.generator

object DependencyInjection{
    private val homeEnv = System.getenv("HOME")
    private val prefixParts = listOf("com", "seanshubin")
    private val nameParts = listOf("kotlin","generated", "multiplatform")
    private val githubRelative = listOf("github", "sean")
    private val namesAndPaths:Names = Names(homeEnv, prefixParts, nameParts, githubRelative)
    private val frontEndEntry= Module(listOf("js","app"), CommonModule)
    private val backEndEntry= Module(listOf("jvm","app"), CommonModule)
    private val frontEndPlatform= Module(listOf("js","frontend"), JsModule)
    private val backEndPlatform= Module(listOf("jvm", "backend"), CommonModule)
    private val frontEndCommon= Module(listOf("common","frontend"), CommonModule)
    private val backEndCommon= Module(listOf("common","backend"), CommonModule)
    private val genericCommon= Module(listOf("common","generic"), CommonModule)
    private val moduleNames:List<Module> = listOf(
            frontEndEntry,
            backEndEntry,
            frontEndPlatform,
            backEndPlatform,
            frontEndCommon,
            backEndCommon,
            genericCommon
    )
    private val parentGenerator:ParentGenerator = ParentGenerator(namesAndPaths, moduleNames)
    val application:Runnable = ProjectGenerator(parentGenerator)
}
