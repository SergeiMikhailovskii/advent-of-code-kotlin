fun main() {
    val input = readInput("Day10_test")
    val addsArr = mutableListOf<Int>()
    var x = 1 //start sprite
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
        if (index % 40 >= x && index % 40 < x + 3) {
            print("█")
        } else {
            print(' ')
        }
        x += i
        if ((index + 1) % 40 == 0) {
            x = 0
            print("\n")
        }
    }
}