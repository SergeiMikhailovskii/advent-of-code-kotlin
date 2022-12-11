fun main() {
    val input = readInput("Day11_test")
    val monkeys = mutableListOf<Monkey>()
    input.filter { it.isNotEmpty() }.chunked(6).map {
        val items = it[1].substringAfterLast("Starting items: ").split(", ").map(String::toInt)
        val operation = it[2].substringAfterLast("Operation: new = ").split(" ")
        val divisibleBy = it[3].substringAfterLast("Test: divisible by ").toInt()
        val ifTrueTo = it[4].substringAfterLast("If true: throw to monkey ").toInt()
        val ifFalseTo = it[5].substringAfterLast("If false: throw to monkey ").toInt()
        monkeys.add(
            Monkey(
                items = items.toMutableList(),
                divideOn = divisibleBy,
                ifTrueTo = ifTrueTo,
                ifFalseTo = ifFalseTo,
                operation = Monkey.Operation(
                    action = operation[1],
                    value = operation[2].toIntOrNull()
                )
            )
        )
    }
    for (i in 0 until 20) {
        monkeys.forEach { monkey ->
            val iterator = monkey.items.iterator()
            monkey.intersections += monkey.items.size
            while (iterator.hasNext()) {
                val it = iterator.next()
                val item = (if (monkey.operation.action == "*") {
                    it * (monkey.operation.value ?: it)
                } else {
                    it + (monkey.operation.value ?: it)
                }) / 3
                if (item % monkey.divideOn == 0) {
                    monkeys[monkey.ifTrueTo].items.add(item)
                } else {
                    monkeys[monkey.ifFalseTo].items.add(item)
                }
                iterator.remove()
            }
        }
    }

    val result = monkeys.map { it.intersections }.sortedDescending().take(2).reduce { acc, i -> acc * i }

    println(result)
}

class Monkey(
    val items: MutableList<Int>,
    val divideOn: Int,
    val ifTrueTo: Int,
    val ifFalseTo: Int,
    val operation: Operation,
    var intersections: Int = 0
) {
    class Operation(
        val action: String,
        val value: Int?
    )
}