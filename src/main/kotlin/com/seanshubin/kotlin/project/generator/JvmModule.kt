package com.seanshubin.kotlin.project.generator

class JvmModule(parent: Parent,
                moduleNameParts: List<String>,
                dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine }
        return listOf(
                "apply plugin: \"kotlin-platform-jvm\"",
                "",
                "archivesBaseName = \"$archivesBaseName\"",
                "",
                "dependencies {") +
                dependencyLines +
                listOf(
                        "    compile libraries.kotlin_stdlib",
                        "    testCompile libraries.kotlin_test_junit",
                        "}",
                        "",
                        "task sourcesJar(type: Jar) {",
                        "    classifier = \"sources\"",
                        "    from sourceSets.main.kotlin",
                        "}",
                        "",
                        "artifacts {",
                        "    archives sourcesJar",
                        "}"
                )
    }

    override fun generateFiles() {
        generateImplementation()
        generateTest()
    }
}
