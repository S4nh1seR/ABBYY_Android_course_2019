package main

import kotlin.reflect.KProperty
import java.lang.IllegalArgumentException

class StorageDelegate<T> constructor(val key: String, value: T) {

    private val storage = Storage()

    private fun putValue(value: T) {
        when (value) {
            is String -> storage.put(key, value as String)
            is Char -> storage.put(key, value as Char)
            is Int -> storage.put(key, value as Int)
            is Long -> storage.put(key, value as Long)
            is Double -> storage.put(key, value as Double)
            else -> throw IllegalArgumentException()
        }
    }

    init {
        putValue(value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = storage.get(key) as T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putValue(value)
}