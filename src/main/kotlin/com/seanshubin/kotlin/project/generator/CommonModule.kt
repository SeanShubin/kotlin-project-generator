package com.seanshubin.kotlin.project.generator

class CommonModule(parent: Parent,
                   moduleNameParts: List<String>,
                   dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine(this) }
        return listOf(
                "apply plugin: \"kotlin-platform-common\"",
                "",
                "archivesBaseName = \"${archivesBaseName}\"",
                "",
                "dependencies {") +
                dependencyLines +
                listOf(
                        "    compile libraries.kotlin_stdlib_common",
                        "    testCompile libraries.kotlin_test_annotations_common",
                        "    testCompile libraries.kotlin_test_common",
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

    override fun dependsWord(client: Module): String =
            when (client) {
                is PlatformModule -> "expectedBy"
                else -> "compile"
            }

    override fun generateFiles() {
        generateImplementation()
        generateTest()
    }
}
