package com.seanshubin.kotlin.project.generator

class ModuleGenerator(private val module: Module) {
    fun generate() {
        generateBuild()
        generateFiles()
    }

    private fun generateBuild() {
        FileUtil.writeLinesToFile(module.buildPath, module.buildFileContent())
    }

    private fun generateFiles() {
        if (module.needsSampleTest) {
            generateImplementation()
            generateTest()
        }
    }

    private fun generateImplementation() {
        FileUtil.writeLinesToFile(module.sampleImplementationPath, generateImplementationLines())
    }

    private fun generateImplementationLines(): List<String> {
        val lines = listOf(
                "package ${module.packageName}",
                "",
                "class ${module.implementationName} {",
                "    fun greet(target:String):String = \"Hello, ${'$'}target!\"",
                "}"
        )
        return lines
    }

    private fun generateTest() {
        FileUtil.writeLinesToFile(module.sampleTestPath, generateTestLines())
    }

    private fun generateTestLines(): List<String> {
        val lines = listOf(
                "package ${module.packageName}",
                "",
                "import kotlin.test.Test",
                "import kotlin.test.assertEquals",
                "",
                "class ${module.testName} {",
                "    @Test",
                "    fun greetTest() {",
                "        val greeter = ${module.implementationName}()",
                "        assertEquals(\"Hello, world!\", greeter.greet(\"world\"))",
                "    }",
                "}"
        )
        return lines
    }
}
