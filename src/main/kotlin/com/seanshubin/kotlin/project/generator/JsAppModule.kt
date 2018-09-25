package com.seanshubin.kotlin.project.generator

class JsAppModule(parent: Parent,
                  moduleNameParts: List<String>,
                  dependencies: List<Module>) : Module(
        parent,
        moduleNameParts,
        dependencies) {
    override fun buildFileContent(): List<String> {
        val dependencyLines = dependencies.map { it.dependencyLine }
        return listOf(
                "import com.google.javascript.jscomp.CompilerOptions",
                "",
                "apply plugin: \"kotlin2js\"",
                "apply plugin: \"kotlin-dce-js\"",
                "apply plugin: \"com.eriwen.gradle.js\"",
                "",
                "dependencies {") +
                dependencyLines +
                listOf(
                        "    compile libraries.kotlin_stdlib_js",
                        "}",
                        "",
                        "[compileKotlin2Js, compileTestKotlin2Js]*.configure {",
                        "    kotlinOptions.moduleKind = \"umd\"",
                        "    kotlinOptions.sourceMap = true",
                        "    kotlinOptions.sourceMapEmbedSources = \"always\"",
                        "}",
                        "",
                        "combineJs {",
                        "    source = [",
                        "            \"${'$'}{buildDir}/kotlin-js-min/main/kotlin.js\",",
                        "            \"${'$'}{buildDir}/kotlin-js-min/main/kotlin-multiplatform-recipes-js.js\",",
                        "            \"${'$'}{buildDir}/kotlin-js-min/main/js-app.js\"",
                        "    ]",
                        "    dest = file(\"${'$'}{temporaryDir}/${'$'}{archivesBaseName}-combined.js\")",
                        "}",
                        "",
                        "minifyJs {",
                        "    source = combineJs",
                        "    dest = file(\"${'$'}{temporaryDir}/${'$'}{archivesBaseName}-minified.js\")",
                        "    closure {",
                        "        compilerOptions = new CompilerOptions().with {",
                        "            setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT5)",
                        "            return it",
                        "        }",
                        "    }",
                        "}",
                        "",
                        "combineJs.dependsOn runDceKotlinJs",
                        "build.dependsOn minifyJs",
                        "",
                        "task minifiedJar(type: Jar) {",
                        "    classifier = \"minified\"",
                        "    from minifyJs",
                        "}",
                        "",
                        "artifacts {",
                        "    archives minifiedJar",
                        "}"
                )
    }

    override fun generateFiles() {
    }
}
