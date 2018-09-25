package com.seanshubin.kotlin.project.generator

object CommonModule : ModuleType() {
    override fun buildFileContent(names: ModuleNames): List<String> = listOf(
            "apply plugin: \"kotlin-platform-common\"",
            "",
            "archivesBaseName = \"${names.archivesBaseName}\"",
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
