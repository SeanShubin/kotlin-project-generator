package com.seanshubin.kotlin.project.generator

class ModuleGenerator(private val names: ModuleNames) {

    fun generate() {
        generateBuild()
        generateFiles()
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(names.buildPath, generateBuildContent())
    }

    private fun generateBuildContent(): List<String> {
        return listOf(
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
                "}",
                "")
    }

    private fun generateFiles() {
        generateImplementation()
        generateTest()
    }

    private fun generateImplementation() {
        FileUtil.writeLinesToFile(names.sampleImplementationPath, generateImplementationLines())
    }

    private fun generateImplementationLines(): List<String> {
        val lines = listOf(
                "package ${names.packageName}",
                "",
                "class ${names.implementationName} {",
                "    fun greet(target:String):String = \"Hello, ${'$'}target!\"",
                "}"
        )
        return lines
    }

    private fun generateTest() {
        FileUtil.writeLinesToFile(names.sampleTestPath, generateTestLines())
    }

    private fun generateTestLines(): List<String> {
        val lines = listOf(
                "package ${names.packageName}",
                "",
                "import kotlin.test.Test",
                "import kotlin.test.assertEquals",
                "",
                "class ${names.testName} {",
                "    @Test",
                "    fun greetTest() {",
                "        val greeter = ${names.implementationName}()",
                "        assertEquals(\"Hello, world!\", greeter.greet(\"world\"))",
                "    }",
                "}"
        )
        return lines
    }
}
