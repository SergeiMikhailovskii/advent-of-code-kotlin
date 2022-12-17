import kotlin.math.max
import kotlin.math.min

fun main() {
    class Point(
        val x: Int,
        val y: Int
    )

    val initial = 500

    val input = readInput("Day14_test")
    val mapped = input.map {
        it.split(" -> ").map {
            val arr = it.split(",")
            Point(arr[0].toInt(), arr[1].toInt())
        }
    }
    var maxY = Int.MIN_VALUE
    for (i in mapped.indices) {
        for (j in mapped[i].indices) {
            maxY = max(maxY, mapped[i][j].y)
        }
    }
    val minX = initial - maxY - 2
    val maxX = initial + maxY + 2
    val width = maxX - minX + 1

    val matrix = Array(maxY + 3) { Array(width) { "." } }
    for (j in matrix.last().indices) {
        matrix[matrix.size - 1][j] = "#"
    }

    mapped.forEach {
        var start = it[0]
        for (i in 1 until it.size) {
            val end = it[i]
            if (start.x == end.x) {
                for (j in min(start.y, end.y)..max(start.y, end.y)) {
                    matrix[j][start.x - minX] = "#"
                }
            } else if (start.y == end.y) {
                for (j in min(start.x, end.x)..max(start.x, end.x)) {
                    matrix[start.y][j - minX] = "#"
                }
            }
            start = end
        }
    }
    fun moveLower(point: Point): Boolean {
        if (matrix[point.y][point.x] == ".") {
            if (!moveLower(Point(y = point.y + 1, x = point.x))) {
                matrix[point.y][point.x] = "0"
            }
            return true
        } else if (matrix[point.y][point.x - 1] == ".") {
            if (!moveLower(Point(y = point.y + 1, x = point.x - 1))) {
                matrix[point.y][point.x - 1] = "0"
            }
            return true
        } else if (matrix[point.y][point.x + 1] == ".") {
            if (!moveLower(Point(y = point.y + 1, x = point.x + 1))) {
                matrix[point.y][point.x + 1] = "0"
            }
            return true
        }
        return false
    }

    var counter = 0
    try {
        while (true) {
            moveLower(Point(initial - minX, 0))
            counter++
        }
    } catch (e: ArrayIndexOutOfBoundsException) {
        println(counter)
    }


}
