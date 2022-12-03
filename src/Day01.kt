fun main() {
    val inputList = readInput("Day01_test")
    val mappedSums = mutableListOf<Int>()
    inputList.reduce { acc, s ->
        if (s.isEmpty()) {
            mappedSums.add(acc.toInt())
            "0"
        } else {
            (acc.toInt() + s.toInt()).toString()
        }
    }
    mappedSums.sortDescending()
    println(mappedSums.take(3).sum())
}
