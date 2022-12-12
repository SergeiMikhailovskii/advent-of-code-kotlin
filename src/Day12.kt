val queue = ArrayDeque<Point>()
val matrixElementsWithDistanceToEnd = mutableMapOf<Point, Int>()
var width: Int = 0
var height: Int = 0
var input: List<String> = listOf()

fun main() {
    input = readInput("Day12_test")
    var end = Point(0, 0)
    input.forEachIndexed { i, line ->
        line.forEachIndexed { j, it ->
            if (it == 'E') {
                end = Point(i, j)
            }
        }
    }
    width = input[0].length
    height = input.size
    queue.add(end)
    matrixElementsWithDistanceToEnd[end] = 0

    while (queue.isNotEmpty()) {
        val currentCell = queue.removeFirst()
        val distance = matrixElementsWithDistanceToEnd[currentCell]!! + 1
        val itemValue = itemValue(input[currentCell.i][currentCell.j])
        goNext(currentCell.i + 1, currentCell.j, distance, itemValue)
        goNext(currentCell.i - 1, currentCell.j, distance, itemValue)
        goNext(currentCell.i, currentCell.j + 1, distance, itemValue)
        goNext(currentCell.i, currentCell.j - 1, distance, itemValue)
    }

    var distance = Int.MAX_VALUE
    for (i in 0 until height) {
        for (j in 0 until width) {
            if (input[i][j] == 'a') {
                val distanceOfCell = matrixElementsWithDistanceToEnd[Point(i, j)] ?: Int.MAX_VALUE
                distance = minOf(distance, distanceOfCell)
            }
        }
    }
    println(distance)
}

fun goNext(i: Int, j: Int, d: Int, prevItemValue: Int) {
    if (i in 0 until height && j in 0 until width) {
        val point = Point(i, j)
        val newValue = itemValue(input[i][j])
        if (newValue + 1 >= prevItemValue && !matrixElementsWithDistanceToEnd.containsKey(point)) {
            matrixElementsWithDistanceToEnd[point] = d
            queue.add(point)
        }
    }
}

fun itemValue(c: Char) = when (c) {
    'S' -> 0
    'E' -> 'z' - 'a'
    else -> c - 'a'
}

data class Point(
    val i: Int,
    val j: Int
)