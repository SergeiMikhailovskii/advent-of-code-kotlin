fun main() {
    val input = readInput("Day10_test")
    val addsArr = mutableListOf<Int>()
    var x = 1
    var sum = 0
    input.forEach {
        if (it == "noop") {
            addsArr.add(0)
        } else {
            val add = it.split(" ")[1].toInt()
            addsArr.add(0)
            addsArr.add(add)
        }
    }
    addsArr.forEachIndexed { index, i ->
        x += i
        if ((index - 18) % 40 == 0) {
            sum += x * (index + 2)
        }
    }
    println(sum)
}