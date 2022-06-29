package com.digyth.noobeal

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray

val gson = GsonBuilder().setPrettyPrinting().create()

fun JsonArray.toStringArray(): Array<String> {
    val array = Array(size()) { "" }
    for (i in 0 until size()) {
        array[i] = get(i).asString
    }
    return array
}