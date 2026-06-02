package com.example.monumentos_practica6.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyMonumentPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveMonuments(monuments: List<MonumentBo>) {
        val json = gson.toJson(monuments)
        prefs.edit().putString(KEY_MONUMENTS, json).apply()
    }

    fun getMonuments(): List<MonumentBo> {
        val json = prefs.getString(KEY_MONUMENTS, null) ?: return emptyList()
        val type = object : TypeToken<List<MonumentBo>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        private const val PREFS_NAME = "my_monuments_prefs"
        private const val KEY_MONUMENTS = "saved_monuments"
    }
}