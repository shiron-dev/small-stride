package dev.shiron.smallstride.repository

import dev.shiron.smallstride.model.TargetClass

private val wipData = mutableMapOf<String, TargetClass>()

fun saveWip(targetClass: TargetClass): TargetClass {
    wipData[targetClass.fileName] = targetClass
    return targetClass
}

fun readWip(fileName: String): TargetClass? {
    return wipData[fileName]
}
