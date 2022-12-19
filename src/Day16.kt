fun main() {

    val timeLimit = 26

    class Valve(
        val rate: Int,
        val tunnels: List<String>,
    )

    class PathB(
        val valvesMe: List<Valve>,
        val valvesElephant: List<Valve>,
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
    val startPath = PathB(
        valvesMe = listOf(start),
        valvesElephant = listOf(start),
        opened = hashMapOf()
    )
    var allPaths = listOf(startPath)
    var bestPath = startPath
    var time = 1

    while (time < timeLimit) {
        val newPaths = mutableListOf<PathB>()

        for (currentPath in allPaths) {
            if (currentPath.opened.size == maxOpenedValves) continue

            val currentLastMe = currentPath.valvesMe.last()
            val currentValvesMe = currentPath.valvesMe

            val currentLastElephant = currentPath.valvesElephant.last()
            val currentValvesElephant = currentPath.valvesElephant

            val openMe = currentLastMe.rate > 0 && !currentPath.opened.containsKey(currentLastMe)
            val openElephant = currentLastElephant.rate > 0 && !currentPath.opened.containsKey(currentLastElephant)

            if (openMe || openElephant) {
                val opened = currentPath.opened.toMutableMap()

                val possibleValvesMe = if (openMe) {
                    opened[currentLastMe] = time
                    listOf(currentValvesMe + currentLastMe)
                } else {
                    currentLastMe.tunnels.map {
                        val valve = valves[it]!!
                        val possibleValves = currentValvesMe + valve
                        possibleValves
                    }
                }

                val possibleValvesElephant = if (openElephant) {
                    opened[currentLastElephant] = time
                    listOf(currentValvesElephant + currentLastElephant)
                } else {
                    currentLastElephant.tunnels.map {
                        val valve = valves[it]!!
                        val possibleValves = currentValvesElephant + valve
                        possibleValves
                    }
                }

                for (me in possibleValvesMe) {
                    for (elephant in possibleValvesElephant) {
                        val possibleNewPath = PathB(me, elephant, opened)
                        newPaths += possibleNewPath
                    }
                }
            }

            val combinedLeads = currentLastMe.tunnels.map { me ->
                currentLastElephant.tunnels.map { elephant ->
                    me to elephant
                }
            }.flatten()
                .filter { (a, b) -> a != b }

            val possiblePaths = combinedLeads.map { (me, elephant) ->
                val valveMe = valves[me]!!
                val valveElephant = valves[elephant]!!

                val possibleValvesMe = currentValvesMe + valveMe
                val possibleValvesElephant = currentValvesElephant + valveElephant
                val possiblePath = PathB(possibleValvesMe, possibleValvesElephant, currentPath.opened)
                possiblePath
            }

            newPaths.addAll(possiblePaths)
        }

        allPaths = newPaths.sortedByDescending { it.total }.take(200000)

        if (allPaths.first().total > bestPath.total) {
            bestPath = allPaths.first()
        }

        time++
    }
    println(bestPath.total)
}