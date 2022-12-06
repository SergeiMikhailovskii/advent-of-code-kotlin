fun main() {
    val input = readInput("Day06_test").first()
    for (i in 0 until input.length - 14) {
        val window = input.substring(i, i + 14)
        if (window.all(mutableSetOf<Char>()::add)) {
            println(i + 14)
            println(window)
            break
        }
    }
}