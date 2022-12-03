fun main() {
    val inputList = readInput("Day03_test")
    var totalSum = 0
    for (i in inputList.indices step 3) {
        val firstElve = inputList[i].toSet()
        val secondElve = inputList[i + 1].toSet()
        val thirdElve = inputList[i + 2].toSet()
        val firstIntersect = firstElve intersect secondElve
        val secondIntersect = firstIntersect intersect thirdElve
        val it = secondIntersect.first()
        totalSum += if (it in 'A'..'Z') it.code - 38 else it.code - 96
    }
    println(totalSum)
}