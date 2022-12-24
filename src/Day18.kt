import kotlin.math.abs

fun main() {
    class Cube(
        val x: Int,
        val y: Int,
        val z: Int
    )

    val cubes = readInput("Day18_test").map {
        val coords = it.split(',')
        Cube(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
    }
    var linkedCubesAmount = 0
    for (i in cubes) {
        for (j in cubes) {
            if (i.z == j.z && ((i.x == j.x && abs(i.y - j.y) == 1) || (i.y == j.y && abs(i.x - j.x) == 1))) {
                linkedCubesAmount++
            } else if (i.x == j.x && ((i.z == j.z && abs(i.y - j.y) == 1) || (i.y == j.y && abs(i.z - j.z) == 1))) {
                linkedCubesAmount++
            } else if (i.y == j.y && ((i.z == j.z && abs(i.x - j.x) == 1) || (i.x == j.x && abs(i.z - j.z) == 1))) {
                linkedCubesAmount++
            }
        }
    }
    println(cubes.size * 6 - linkedCubesAmount)

}