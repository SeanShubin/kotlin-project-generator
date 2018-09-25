package com.seanshubin.kotlin.project.generator

class Module(val nameParts: List<String>,
             val moduleType: ModuleType,
             val dependencies: List<Module>)
