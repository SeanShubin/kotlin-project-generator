package com.seanshubin.kotlin.project.generator

object TrymeDependencyInjection {
    private val homeEnv = System.getenv("HOME")
    private val prefixParts = listOf("com", "seanshubin")
    private val nameParts = listOf("kotlin", "tryme")
    private val githubRelative = listOf("github", "generated")
    private val parent: Parent = Parent(homeEnv, prefixParts, nameParts, githubRelative)
    private val common = CommonModule(parent, listOf("common"), emptyList())
    private val js = JsModule(parent, listOf("js"), listOf(common))
    private val jvm = JvmModule(parent, listOf("jvm"), listOf(common))
    private val modules: List<Module> = listOf(
            common,
            js,
            jvm
    )
    private val parentGenerator: ParentGenerator = ParentGenerator(parent, modules, versionsForNow)
    val application: Runnable = ProjectGenerator(parentGenerator)
}
