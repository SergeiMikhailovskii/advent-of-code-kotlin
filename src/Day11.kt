fun main() {
    val input = readInput("Day11_test")
    val monkeys = mutableListOf<Monkey>()
    val divideOnList = mutableListOf<Int>()
    input.filter { it.isNotEmpty() }.chunked(6).forEach {
        val items = it[1].substringAfterLast("Starting items: ").split(", ").map(String::toInt)
        val operation = it[2].substringAfterLast("Operation: new = ").split(" ")
        val divisibleBy = it[3].substringAfterLast("Test: divisible by ").toInt()
        val ifTrueTo = it[4].substringAfterLast("If true: throw to monkey ").toInt()
        val ifFalseTo = it[5].substringAfterLast("If false: throw to monkey ").toInt()
        monkeys.add(
            Monkey(
                items = items.map(Monkey::Item).toMutableList(),
                ifTrueTo = ifTrueTo,
                ifFalseTo = ifFalseTo,
                operation = Monkey.Operation(
                    action = operation[1],
                    value = operation[2].toIntOrNull()
                )
            )
        )
        divideOnList.add(divisibleBy)
    }

    monkeys.forEach {
        it.items.forEach { item ->
            val list = mutableListOf<Int>()
            divideOnList.forEach { divider ->
                list.add(item.value % divider)
            }
            item.divisionResult.addAll(list)
        }
    }

    for (i in 0 until 10_000) {
        monkeys.forEachIndexed { monkeyIndex, monkey ->
            monkey.intersections += monkey.items.size
            val iterator = monkey.items.iterator()
            while (iterator.hasNext()) {
                val it = iterator.next()
                if (monkey.operation.action == "*") {
                    it.divisionResult = it.divisionResult.map { div ->
                        div * (monkey.operation.value ?: div)
                    }.toMutableList()
                } else {
                    it.divisionResult = it.divisionResult.map { div ->
                        div + (monkey.operation.value ?: div)
                    }.toMutableList()
                }
                for (ind in 0 until it.divisionResult.size) {
                    it.divisionResult[ind] %= divideOnList[ind]
                }
                if (it.divisionResult[monkeyIndex] == 0) {
                    monkeys[monkey.ifTrueTo].items.add(it)
                } else {
                    monkeys[monkey.ifFalseTo].items.add(it)
                }
                iterator.remove()
            }
        }
    }

    val result = monkeys.map { it.intersections.toLong() }.sortedDescending().take(2).reduce { acc, i -> acc * i }

    println(result)
}

class Monkey(
    val items: MutableList<Item>,
    val ifTrueTo: Int,
    val ifFalseTo: Int,
    val operation: Operation,
    var intersections: Int = 0,
) {

    class Item(
        val value: Int,
        var divisionResult: MutableList<Int> = mutableListOf()
    )

    class Operation(
        val action: String,
        val value: Int?
    )
}