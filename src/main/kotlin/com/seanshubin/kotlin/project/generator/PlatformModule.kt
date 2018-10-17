package com.seanshubin.kotlin.project.generator

abstract class PlatformModule(parent: Parent,
                              moduleNameParts: List<String>,
                              dependencies: List<Module>) : Module(
        parent, moduleNameParts, dependencies) {
    override fun dependsWord(client: Module): String = "compile"
}
