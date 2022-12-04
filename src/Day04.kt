fun main() {
    val inputList = readInput("Day04_test")
    var counter = 0
    inputList.forEach {
        val arr = it.split('-', ',').map { it.toInt() }
        if (arr[2] < arr[0]) {
            if (arr[0] <= arr[3]) {
                counter++
            }
        } else {
            if (arr[2] <= arr[1]) {
                counter++
            }
        }
    }
    println(counter)
}