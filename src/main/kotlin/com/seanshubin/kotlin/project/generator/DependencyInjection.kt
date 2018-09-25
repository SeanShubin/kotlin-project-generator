package com.seanshubin.kotlin.project.generator

object DependencyInjection{
    private val homeEnv = System.getenv("HOME")
    private val prefixParts = listOf("com", "seanshubin")
    private val nameParts = listOf("kotlin","generated", "multiplatform")
    private val githubRelative = listOf("github", "sean")
    private val namesAndPaths:Names = Names(homeEnv, prefixParts, nameParts, githubRelative)
    private val frontEndEntry= listOf("js","app")
    private val backEndEntry= listOf("jvm","app")
    private val frontEndPlatform= listOf("js","frontend")
    private val backEndPlatform= listOf("jvm", "backend")
    private val frontEndCommon= listOf("common","frontend")
    private val backEndCommon= listOf("common","backend")
    private val genericCommon= listOf("common","generic")
    private val moduleNames:List<List<String>> = listOf(
            frontEndEntry,
            backEndEntry,
            frontEndPlatform,
            backEndPlatform,
            frontEndCommon,
            backEndCommon,
            genericCommon
    )
//    private val moduleNames:List<List<String>> = listOf(listOf("common"))
    private val parentGenerator:ParentGenerator = ParentGenerator(namesAndPaths, moduleNames)
    val application:Runnable = ProjectGenerator(parentGenerator)
}
