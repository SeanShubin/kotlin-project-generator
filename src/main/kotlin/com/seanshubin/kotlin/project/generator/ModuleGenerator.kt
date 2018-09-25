package com.seanshubin.kotlin.project.generator

class ModuleGenerator(private val names: ModuleNames) {


    fun generate() {
        generateBuild()
        generateFiles()
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(names.buildPath, names.buildFileContent())
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
