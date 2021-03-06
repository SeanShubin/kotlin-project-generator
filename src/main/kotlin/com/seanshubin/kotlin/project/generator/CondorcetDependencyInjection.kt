package com.seanshubin.kotlin.project.generator

object CondorcetDependencyInjection {
    private val homeEnv = System.getenv("HOME")
    private val prefixParts = listOf("com", "seanshubin")
    private val nameParts = listOf("condorcet")
    private val githubRelative = listOf("github", "sean")
    private val parent: Parent = Parent(homeEnv, prefixParts, nameParts, githubRelative, "condorcet3")
    private val genericCommon = CommonModule(parent, listOf("common", "generic"), emptyList())
    private val frontEndCommon = CommonModule(parent, listOf("common", "frontend"), listOf(genericCommon))
    private val backEndCommon = CommonModule(parent, listOf("common", "backend"), listOf(genericCommon))
    private val frontEndPlatform = JsModule(parent, listOf("js", "frontend"), listOf(genericCommon, frontEndCommon))
    private val backEndPlatform = JvmModule(parent, listOf("jvm", "backend"), listOf(genericCommon, backEndCommon))
    private val frontEndEntry = JsAppModule(parent, listOf("js", "app"), listOf(frontEndPlatform))
    private val backEndEntry = JvmAppModule(parent, listOf("jvm", "app"), listOf(backEndPlatform))
    private val modules: List<Module> = listOf(
            frontEndEntry,
            backEndEntry,
            frontEndPlatform,
            backEndPlatform,
            frontEndCommon,
            backEndCommon,
            genericCommon
    )
    private val parentGenerator: ParentGenerator = ParentGenerator(parent, modules, versionsForNow)
    val application: Runnable = ProjectGenerator(parentGenerator)
}
