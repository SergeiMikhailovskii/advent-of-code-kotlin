import kotlin.math.max
import kotlin.math.min

fun main() {
    class Point(
        val x: Int,
        val y: Int
    )

    val input = readInput("Day14_test")
    val mapped = input.map {
        it.split(" -> ").map {
            val arr = it.split(",")
            Point(arr[0].toInt(), arr[1].toInt())
        }
    }
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var maxY = Int.MIN_VALUE
    for (i in mapped.indices) {
        for (j in mapped[i].indices) {
            minX = min(minX, mapped[i][j].x)
            maxX = max(maxX, mapped[i][j].x)
            maxY = max(maxY, mapped[i][j].y)
        }
    }
    val width = maxX - minX + 1

    val matrix = Array(maxY) { Array(width) { "." } }

    mapped.forEach {
        var start = it[0]
        for (i in 1 until it.size) {
            val end = it[i]
            if (start.x == end.x) {
                for (j in min(start.y, end.y)..max(start.y, end.y)) {
                    matrix[j - 1][start.x - minX] = "#"
                }
            } else if (start.y == end.y) {
                for (j in min(start.x, end.x)..max(start.x, end.x)) {
                    matrix[start.y - 1][j - minX] = "#"
                }
            }
            start = end
        }
    }
    fun moveLower(point: Point): Boolean {
        if (point.x == 0 && point.y == 8) {
            println()
        }
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
        } else if (point.x < width - 1 && matrix[point.y][point.x + 1] == ".") {
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
            moveLower(Point(500 - minX, 0))
            counter++
        }
    } catch (e: ArrayIndexOutOfBoundsException) {
        println(counter)
    }

}
