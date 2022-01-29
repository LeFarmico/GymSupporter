package com.lefarmico.data.utils

import android.content.res.Resources
import android.util.Log
import androidx.annotation.RawRes
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import java.nio.charset.Charset

class JSONResourceReader(res: Resources, @RawRes val id: Int) {

    private val resReader = res.openRawResource(id)
    private val writer = StringWriter()
    var jsonString = ""

    init {
        try {
            val reader = BufferedReader(InputStreamReader(resReader, Charset.defaultCharset()))
            var line = reader.readLine()
            while (!line.isNullOrEmpty()) {
                writer.write(line)
                line = reader.readLine()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unhandled exception while using JSONResourceReader", e)
        } finally {
            try {
                resReader.close()
            } catch (e: Exception) {
                Log.e(TAG, "Unhandled exception while using JSONResourceReader", e)
            }
        }
        jsonString = writer.toString()
    }

    fun <T> constructWithGson(type: Class<T>): T {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonString, type)
    }

    companion object {
        const val TAG = "JSONResourceReader"
    }
}
