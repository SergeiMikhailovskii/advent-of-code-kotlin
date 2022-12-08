fun main() {
    val input = readInput("Day08_test")
    val matrix = Array(input.size) { Array(input[it].length) { 0 } }
    var visibleTrees = 0

    val scores = mutableListOf<Int>()

    input.forEachIndexed { i, row ->
        row.forEachIndexed { j, it ->
            matrix[i][j] = it.digitToInt()
        }
    }

    // Part2
    for (i in 1 until matrix.size - 1) {
        for (j in 1 until matrix[i].size - 1) {
            val tree = matrix[i][j]
            var scenicScore = 1

            // left
            var steps = 0
            for (k in j - 1 downTo 0) {
                steps++
                if (matrix[i][k] >= tree) {
                    break
                }
            }
            scenicScore *= steps
            steps = 0

            // right
            for (k in j + 1 until matrix[i].size) {
                steps++
                if (matrix[i][k] >= tree) {
                    break
                }
            }
            scenicScore *= steps
            steps = 0

            // top
            for (k in i - 1 downTo 0) {
                steps++
                if (matrix[k][j] >= tree) {
                    break
                }
            }
            scenicScore *= steps
            steps = 0

            // bottom
            for (k in i + 1 until matrix.size) {
                steps++
                if (matrix[k][j] >= tree) {
                    break
                }
            }
            scenicScore *= steps

            scores.add(scenicScore)
        }
    }

    println(scores.max())

    // Part1
    for (i in 1 until matrix.size - 1) {
        for (j in 1 until matrix[i].size - 1) {
            val tree = matrix[i][j]
            var isVisible = true

            for (k in 0 until i) {
                if (matrix[k][j] >= tree) {
                    isVisible = false
                    break
                }
            }

            if (isVisible) {
                visibleTrees++
                continue
            }

            isVisible = true

            for (k in 0 until j) {
                if (matrix[i][k] >= tree) {
                    isVisible = false
                    break
                }
            }

            if (isVisible) {
                visibleTrees++
                continue
            }

            isVisible = true

            for (k in (matrix[i].size - 1) downTo j + 1) {
                if (matrix[i][k] >= tree) {
                    isVisible = false
                    break
                }
            }

            if (isVisible) {
                visibleTrees++
                continue
            }

            isVisible = true

            for (k in (matrix.size - 1) downTo i + 1) {
                if (matrix[k][j] >= tree) {
                    isVisible = false
                    break
                }
            }

            if (isVisible) {
                visibleTrees++
                continue
            }
        }
    }

    visibleTrees += matrix.size * 2 + (matrix.size - 2) * 2

    println(visibleTrees)
}