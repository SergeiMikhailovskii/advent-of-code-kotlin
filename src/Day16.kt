fun main() {

    val timeLimit = 30

    class Valve(
        val rate: Int,
        val tunnels: List<String>,
    )

    class PathA(
        val valves: List<Valve>,
        /**
         * Key - valve
         * value - opened time
         */
        val opened: Map<Valve, Int>
    ) {
        val total: Int
            get() = opened.map { (valve, i) -> (timeLimit - i) * valve.rate }.sum()
    }

    val input = readInput("Day16_test")
    val regex = "Valve (\\w+?) has flow rate=(\\d+?); tunnels? leads? to valves? (.+)".toRegex()
    val valves = input.associate {
        val (name, rate, tunnelsStr) = regex.matchEntire(it)!!.destructured
        name to Valve(rate.toInt(), tunnelsStr.split(", "))
    }

    val maxOpenedValves = valves.values.count { it.rate > 0 }
    val start = valves["AA"]!!
    val startPath = PathA(
        valves = listOf(start),
        opened = hashMapOf()
    )
    var allPaths = listOf(startPath)
    var bestPath = startPath
    var time = 1

    while (time < timeLimit) {
        val newPath = mutableListOf<PathA>()

        for (currentPath in allPaths) {
            // try to change to break
            if (currentPath.opened.size == maxOpenedValves) continue

            val currentLast = currentPath.valves.last()
            val currentValves = currentPath.valves

            if (currentLast.rate > 0 && !currentPath.opened.containsKey(currentLast)) {
                val opened = currentPath.opened.toMutableMap()
                opened[currentLast] = time
                val possibleValves = currentValves + currentLast
                val possiblePath = PathA(possibleValves, opened)
                newPath.add(possiblePath)
            }

            val possiblePaths = currentLast.tunnels.map {
                val valve = valves[it]!!
                val possibleValves = currentValves + valve
                val possiblePath = PathA(possibleValves, currentPath.opened)
                possiblePath
            }

            newPath.addAll(possiblePaths)
        }

        allPaths = newPath.sortedByDescending { it.total }.take(1000)

        if (allPaths.first().total > bestPath.total) {
            bestPath = allPaths.first()
        }

        time++
    }
    println(bestPath.total)
}