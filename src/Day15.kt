import kotlin.math.abs

fun main() {
    val input = readInput("Day15_test")

    class Sensor(
        val x: Int,
        val y: Int,
        val d: Int
    )

    class Beacon(
        val x: Int,
        val y: Int
    )

    val sensors = mutableListOf<Sensor>()
    val beacons = mutableListOf<Beacon>()

    var minB = Int.MAX_VALUE
    var maxB = Int.MIN_VALUE
    var maxD = Int.MIN_VALUE
    input.forEach {
        val matchResult = Regex("Sensor at x=(\\d+), y=(\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)").find(it)
        val (sx, sy, bx, by) = matchResult!!.destructured
        val d = abs(bx.toInt() - sx.toInt()) + abs(by.toInt() - sy.toInt())
        maxB = maxOf(maxB, bx.toInt())
        maxB = maxOf(maxB, sx.toInt())
        minB = minOf(minB, bx.toInt())
        minB = minOf(minB, sx.toInt())
        maxD = maxOf(maxD, d)
        sensors.add(Sensor(sx.toInt(), sy.toInt(), d))
        beacons.add(Beacon(bx.toInt(), by.toInt()))
    }
    var total = 0
    for (i in minB - maxD..maxB + maxD) {
        val py = 2000000
        if (beacons.any { it.x == i && it.y == py }) {
            continue
        }
        for (it in sensors) {
            if (abs(it.x - i) + abs(it.y - py) <= it.d) {
                total++
                break
            }
        }
    }
    println(total)
}