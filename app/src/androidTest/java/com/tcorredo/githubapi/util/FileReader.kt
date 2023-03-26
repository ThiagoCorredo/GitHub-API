package com.tcorredo.githubapi.util

import android.content.Context
import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun asset(context: Context, assetPath: String): String {
        try {
            val inputStream = context.assets.open(assetPath)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}