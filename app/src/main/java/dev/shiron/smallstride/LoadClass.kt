package dev.shiron.smallstride

import android.content.Context
import com.google.gson.Gson
import dev.shiron.smallstride.domain.TargetClass
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.UUID

fun loadFileList(context: Context):FileListClass {
    return readFile(context, "fileList.json")?.let{Gson().fromJson(it, FileListClass::class.java)} ?: FileListClass(mutableListOf())
}

fun saveTarget(context: Context, targetClass: TargetClass) {
    val fileList = loadFileList(context)
    val fileName = "${UUID.randomUUID()}.json"

    saveFile(context, fileName, Gson().toJson(targetClass))

    fileList.files.add(fileName)
    saveFile(context, "fileList.json", Gson().toJson(fileList))
}

fun loadAllTarget(context: Context):List<TargetClass> {
    val fileList = loadFileList(context)
    val ret = mutableListOf<TargetClass>()
    for (fileName in fileList.files) {
        ret.add(Gson().fromJson(readFile(context, fileName), TargetClass::class.java))
    }
    return ret
}

private fun readFile(context:Context, fileName:String):String?{
    val file = File(context.filesDir, fileName)
    var text: String? = null
    try {
        BufferedReader(FileReader(file)).use { br -> text = br.readLine() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return text
}

private fun saveFile(context:Context, fileName:String, text:String){
    val file = File(context.filesDir, fileName)
    try {
        FileWriter(file).use { writer -> writer.write(text) }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

data class FileListClass(
    var files: MutableList<String>
)