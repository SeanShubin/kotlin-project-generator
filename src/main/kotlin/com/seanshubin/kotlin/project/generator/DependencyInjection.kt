package com.seanshubin.kotlin.project.generator

import java.nio.file.Paths

object DependencyInjection{
    val homeEnv = System.getenv("HOME")
    val home = Paths.get(homeEnv)
    val prefixParts = listOf("com", "seanshubin")
    val nameParts = listOf("kotlin","prototype", "multiplatform")
    val relative = Paths.get("github", "sean")
    val localGithubRoot = home.resolve(relative)
    val frontEndEntry= listOf("js","app")
    val backEndEntry= listOf("jvm","app")
    val frontEndPlatform= listOf("js","frontend")
    val backEndPlatform= listOf("jvm", "backend")
    val frontEndCommon= listOf("common","frontend")
    val backEndCommon= listOf("common","backend")
    val genericCommon= listOf("common","generic")
    val moduleNames:List<List<String>> = listOf(
            frontEndEntry,
            backEndEntry,
            frontEndPlatform,
            backEndPlatform,
            frontEndCommon,
            backEndCommon,
            genericCommon
    )
    val parentGenerator:ParentGenerator = ParentGenerator(prefixParts, nameParts, moduleNames, localGithubRoot)
    val application:Runnable = ProjectGenerator(parentGenerator)
}
