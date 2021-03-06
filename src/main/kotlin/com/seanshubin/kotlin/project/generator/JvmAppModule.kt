package com.seanshubin.kotlin.project.generator

class JvmAppModule(parent: Parent,
                   moduleNameParts: List<String>,
                   dependencies: List<Module>) : PlatformModule(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine(this) }
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
                        "}",
                        "",
                        "jar {",
                        "    manifest {",
                        "        attributes 'Main-Class': '${qualifiedMainName}Kt'",
                        "    }",
                        "    from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }",
                        "}")
    }

    override fun generateFiles() {
        generateImplementation()
        generateMain()
    }
}
