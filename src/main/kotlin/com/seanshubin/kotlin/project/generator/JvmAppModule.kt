package com.seanshubin.kotlin.project.generator

class JvmAppModule(parent: Parent,
                   moduleNameParts: List<String>,
                   dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine }
        return listOf(
                "apply plugin: \"kotlin\"",
                "",
                "",
                "dependencies {") +
                dependencyLines +
                listOf(
                        "    compile libraries.kotlin_stdlib",
                        "}",
                        "",
                        "task run(dependsOn: classes, type: JavaExec) {",
                        "    main = \"jvm.JvmAppKt\"",
                        "    classpath = sourceSets.main.runtimeClasspath",
                        "    ignoreExitValue(true)",
                        "}")
    }

    override fun generateFiles() {
    }
}
