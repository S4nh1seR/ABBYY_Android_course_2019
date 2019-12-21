package main

fun main(args: Array<String>) {

    var id_int: Int by StorageDelegate("id_int", 20)
    println(id_int)
    id_int = 2020
    println(id_int)

    var id_str: String by StorageDelegate("id_str", "String Before")
    println(id_str)
    id_str = "String After"
    println(id_str)

    var id_char: Char by StorageDelegate("id_char", 'a')
    println(id_char)
    id_char = 'b'
    println(id_char)

    var id_double: Double by StorageDelegate("id_double", 20.0)
    println(id_double)
    id_double = 2020.0
    println(id_double)
}