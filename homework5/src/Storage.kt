package main

class Storage : IStorage {

    private val map: MutableMap<String, Any?> by lazy { mutableMapOf<String, Any?>() }

    private fun putIntoMap(key: String, value: Any?) {
        val temp = map.toMutableMap()
        temp[key] = value
        temp.toMap()
        map[key] = value // без этого не работает
    }

    override fun put(key: String, value: String): Unit = putIntoMap(key, value)

    override fun put(key: String, value: Int): Unit = putIntoMap(key, value)

    override fun put(key: String, value: Long): Unit = putIntoMap(key, value)

    override fun put(key: String, value: Char): Unit = putIntoMap(key, value)

    override fun put(key: String, value: Double): Unit = putIntoMap(key, value)

    override fun get(key: String): Any? = map[key]

}