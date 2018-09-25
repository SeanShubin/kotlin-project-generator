package com.seanshubin.kotlin.project.generator

class CommonModule(parent: Parent,
                   moduleNameParts: List<String>,
                   dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> = listOf(
            "apply plugin: \"kotlin-platform-common\"",
            "",
            "archivesBaseName = \"${archivesBaseName}\"",
            "",
            "dependencies {",
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
