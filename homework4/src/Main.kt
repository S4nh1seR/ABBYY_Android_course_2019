
fun main(args: Array<String>) {
    val arr : CustomArrayList<Int> = CustomArrayList()
    arr.add(2019)
    arr.add(2021)
    arr.insert(2020, 1)
    arr.delete(0)
    arr.delete(1)
    println(arr[0])
}
