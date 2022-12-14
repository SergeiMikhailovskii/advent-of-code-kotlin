fun main() {
    abstract class Item
    class Value(val value: Int) : Item()
    class Sequence(val items: List<Item>) : Item()

    fun findClosingBracket(index: Int, str: String): Int {
        var count = 1
        var i = index
        while (i < str.length) {
            i++
            if (str[i] == '[') {
                count++
            } else if (str[i] == ']') {
                count--
            }
            if (count == 0) break
        }
        return i
    }

    fun parseIntoSequence(str: String): Sequence {
        val list = mutableListOf<Item>()
        if (str.isEmpty()) return Sequence(emptyList())

        var index = 0
        while (index < str.length) {
            val c = str[index]
            if (c == '[') {
                val end = findClosingBracket(index, str)
                list.add(parseIntoSequence(str.substring(index + 1, end)))
                index = end
            } else if (c != ',') {
                var numberStr = ""
                var current = c
                while (current.isDigit()) {
                    numberStr += current
                    index++
                    if (index == str.length) {
                        break
                    }
                    current = str[index]
                }
                list.add(Value(numberStr.toInt()))
            }
            index++
        }

        return Sequence(list)
    }

    fun compare(first: Value, second: Value) =
        if (first.value == second.value) 0 else if (second.value > first.value) 1 else -1

    fun compare(first: Sequence, second: Sequence): Int {
        val firstIterator = first.items.iterator()
        val secondIterator = second.items.iterator()
        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            val firstItem = firstIterator.next()
            val secondItem = secondIterator.next()
            if (firstItem is Value && secondItem is Value) {
                val result = compare(firstItem, secondItem)
                if (result == -1 || result == 1) return result
            } else if (firstItem is Sequence && secondItem is Sequence) {
                val result = compare(firstItem, secondItem)
                if (result == -1 || result == 1) return result
            } else if (firstItem is Value && secondItem is Sequence) {
                val result = compare(Sequence(listOf(firstItem)), secondItem)
                if (result == -1 || result == 1) return result
            } else if (firstItem is Sequence && secondItem is Value) {
                val result = compare(firstItem, Sequence(listOf(secondItem)))
                if (result == -1 || result == 1) return result
            }
        }
        return if (firstIterator.hasNext() && !secondIterator.hasNext()) -1
        else if (secondIterator.hasNext() && !firstIterator.hasNext()) 1
        else 0
    }

    val input = readInput("Day13_test")
    var total = 0
    input.filter { it.isNotEmpty() }.chunked(2).forEachIndexed { index, pair ->
        val first = parseIntoSequence(pair.first())
        val second = parseIntoSequence(pair.last())
        if (compare(first, second) == 1) {
            total += index + 1
        }
    }
    val arr = input.filter { it.isNotEmpty() }.toMutableList().apply { addAll(listOf("[[2]]", "[[6]]")) }.toTypedArray()

    for (i in 0 until arr.size - 1) {
        for (j in i + 1 until arr.size) {
            val first = parseIntoSequence(arr[i])
            val second = parseIntoSequence(arr[j])
            if (compare(second, first) > 0) {
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }
    }
    println((arr.indexOf("[[2]]") + 1) * (arr.indexOf("[[6]]") + 1))

}