fun main() {
    val input = readInput("Day09_test")
    val head = arrayOf(0, 0)
    val tail = arrayOf(0, 0)
    val uniqueItems = mutableSetOf<String>()
    input.forEach {
        val direction = it.split(" ").first()
        val amount = it.split(" ").last().toInt()

        for (i in 0 until amount) {
            when (direction) {
                "D" -> head[1] += 1
                "L" -> head[0] -= 1
                "U" -> head[1] -= 1
                "R" -> head[0] += 1
            }
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
            uniqueItems.add("${tail[0]} ${tail[1]}")
        }
    }
}