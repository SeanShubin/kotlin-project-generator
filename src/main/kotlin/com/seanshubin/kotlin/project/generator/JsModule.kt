package com.seanshubin.kotlin.project.generator

class JsModule(parent: Parent,
               moduleNameParts: List<String>,
               dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine }
        return listOf(
                "apply plugin: \"kotlin-platform-js\"",
                "apply plugin: \"com.moowork.node\"",
                "",
                "archivesBaseName = \"${archivesBaseName}\"",
                "",
                "dependencies {") +
                dependencyLines +
                listOf(
                        "    compile libraries.kotlin_stdlib_js",
                        "    testCompile libraries.kotlin_test_js",
                        "}",
                        "",
                        "[compileKotlin2Js, compileTestKotlin2Js]*.configure {",
                        "    kotlinOptions.moduleKind = \"umd\"",
                        "    kotlinOptions.sourceMap = true",
                        "    kotlinOptions.sourceMapEmbedSources = \"always\"",
                        "}",
                        "",
                        "task populateNodeModules(type: Copy, dependsOn: compileKotlin2Js) {",
                        "    from compileKotlin2Js.destinationDir",
                        "",
                        "    configurations.testCompile.each {",
                        "        from zipTree(it.absolutePath).matching { include \"*.js\" }",
                        "    }",
                        "",
                        "    into \"${'$'}{buildDir}/node_modules\"",
                        "}",
                        "",
                        "node {",
                        "    version = nodeVersion",
                        "    download = true",
                        "}",
                        "",
                        "task installQunit(type: NpmTask) {",
                        "    inputs.property(\"qunitVersion\", qunitVersion)",
                        "    outputs.dir file(\"node_modules/qunit\")",
                        "",
                        "    args = [\"install\", \"qunit@${'$'}{qunitVersion}\"]",
                        "}",
                        "",
                        "task runQunit(type: NodeTask, dependsOn: [compileTestKotlin2Js, populateNodeModules, installQunit]) {",
                        "    script = file(\"node_modules/qunit/bin/qunit\")",
                        "    args = [projectDir.toPath().relativize(file(compileTestKotlin2Js.outputFile).toPath())]",
                        "}",
                        "",
                        "test.dependsOn runQunit",
                        "",
                        "task sourcesJar(type: Jar) {",
                        "    classifier = \"sources\"",
                        "    from sourceSets.main.kotlin",
                        "}",
                        "",
                        "artifacts {",
                        "    archives sourcesJar",
                        "}")
    }

    override val needsSampleTest: Boolean = true
}
