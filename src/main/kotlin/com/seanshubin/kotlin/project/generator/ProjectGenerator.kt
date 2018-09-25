package com.seanshubin.kotlin.project.generator

class ProjectGenerator(private val parentGenerator: ParentGenerator):Runnable {
    override fun run() {
        parentGenerator.generate()
    }
}
