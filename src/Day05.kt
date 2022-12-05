import java.lang.Exception

fun main() {
    val lists = mutableListOf(
        listOf('f', 'c', 'p', 'g', 'q', 'r'),
        listOf('w', 't', 'c', 'p'),
        listOf('b', 'h', 'p', 'm', 'c'),
        listOf('l', 't', 'q', 's', 'm', 'p', 'r'),
        listOf('p', 'h', 'j', 'z', 'v', 'g', 'n'),
        listOf('d', 'p', 'j'),
        listOf('l', 'g', 'p', 'z', 'f', 'j', 't', 'r'),
        listOf('n', 'l', 'h', 'c', 'f', 'p', 't', 'j'),
        listOf('g', 'v', 'z', 'q', 'h', 't', 'c', 'w')
    )

    val inputList = readInput("Day05_test")
    inputList.forEach {
        val arr = it.split(' ').map { it.toInt() }
        val lastItems = lists[arr[1] - 1].takeLast(arr[0])
        lists[arr[1] - 1] = lists[arr[1] - 1].dropLast(arr[0])
        lists[arr[2] - 1] = lists[arr[2] - 1].toMutableList().apply {
            addAll(lastItems)
        }
    }
    lists.forEach { print(it.last().uppercase()) }

}