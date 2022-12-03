fun main() {
    val inputList = readInput("Day02_test")
    var totalSum = 0
    inputList.forEach {
        val rivalResult = it[0]

        when (it[2]) {
            'X' -> {
                totalSum += 0 + when (rivalResult) {
                    'A' -> 3
                    'B' -> 1
                    else -> 2
                }
            }

            'Y' -> {
                totalSum += 3 + when (rivalResult) {
                    'A' -> 1
                    'B' -> 2
                    else -> 3
                }
            }

            'Z' -> {
                totalSum += 6 + when (rivalResult) {
                    'A' -> 2
                    'B' -> 3
                    else -> 1
                }
            }
        }
    }
    println(totalSum)
}