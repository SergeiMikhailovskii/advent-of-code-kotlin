import kotlin.math.abs

fun main() {
    val input = readInput("Day15_test")

    class Sensor(
        val x: Int,
        val y: Int,
        val d: Int
    )

    val sensors = mutableListOf<Sensor>()

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
    }

    val max = 4_000_000
    class Ev(val x: Int, val d: Int)

    val es = mutableListOf<Ev>()
    for (i in 0..max) {
        es.clear()
        sensors.forEach {
            if (abs(it.y - i) <= it.d) {
                val w = it.d - abs(it.y - i)
                es += Ev(it.x - w, 1)
                es += Ev(it.x + w + 1, -1)
            }
        }
        es.sortWith(compareBy(Ev::x, Ev::d))
        var px = es[0].x
        var cnt = 0
        es.forEach {
            if (it.x > px) {
                if (cnt == 0 && px in 0..max) {
                    println("${px * max.toLong() + i}")
                }
                px = it.x
            }
            cnt += it.d
        }
    }
}