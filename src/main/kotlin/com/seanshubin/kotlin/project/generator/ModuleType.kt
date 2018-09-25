package com.seanshubin.kotlin.project.generator

abstract class ModuleType {
    abstract fun buildFileContent(names: ModuleNames): List<String>
}
