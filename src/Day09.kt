val uniqueItems = mutableSetOf<String>()

fun main() {
    val input = readInput("Day09_test")
    val snake = Array(10) {
        arrayOf(0, 0)
    }
    input.forEach {
        val direction = it.split(" ").first()
        val amount = it.split(" ").last().toInt()

        for (i in 0 until amount) {
            when (direction) {
                "D" -> snake.first()[1] += 1
                "L" -> snake.first()[0] -= 1
                "U" -> snake.first()[1] -= 1
                "R" -> snake.first()[0] += 1
            }
            for (j in 0 until snake.size - 1) {
                move(snake[j], snake[j + 1], j + 1 == snake.size - 1)
            }
        }
    }
    println(uniqueItems.size)
}

fun move(head: Array<Int>, tail: Array<Int>, isLast: Boolean) {
    if (head[0] == tail[0]) {
        if (head[1] - tail[1] > 1) {
            tail[1] += 1
        } else if (head[1] - tail[1] < -1) {
            tail[1] -= 1
        }
    } else if (head[1] == tail[1]) {
        if (head[0] - tail[0] > 1) {
            tail[0] += 1
        } else if (head[0] - tail[0] < -1) {
            tail[0] -= 1
        }
    } else if ((head[0] - tail[0] > 1 && head[1] > tail[1]) || (head[1] - tail[1] > 1 && head[0] > tail[0])) {
        tail[0] += 1
        tail[1] += 1
    } else if ((head[0] - tail[0] < -1 && head[1] > tail[1]) || (head[1] - tail[1] > 1 && head[0] < tail[0])) {
        tail[0] -= 1
        tail[1] += 1
    } else if ((head[0] - tail[0] < -1 && head[1] < tail[1]) || (head[1] - tail[1] < -1 && head[0] < tail[0])) {
        tail[0] -= 1
        tail[1] -= 1
    } else if ((head[0] - tail[0] > 1 && head[1] < tail[1]) || (head[1] - tail[1] < -1 && head[0] > tail[0])) {
        tail[0] += 1
        tail[1] -= 1
    }

    if (isLast) uniqueItems.add("${tail[0]} ${tail[1]}")
}