fun main() {

    fun part1(input: List<String>): Int {
        val common = input.map { it.toCharArray().map { c -> c - '0' } }.rotate().map { it.sum() }
            .map { if (it >= input.size - it) 1 else 0 }
        val uncommon = common.map { 1 - it }

        val gamma = common.joinToString("") { it.toString() }.toInt(2)
        val epsilon = uncommon.joinToString("") { it.toString() }.toInt(2)

        return gamma * epsilon;
    }

    fun part2(input: List<String>): Int {
        val bits = input.map { it.toCharArray().map { c -> c - '0' } }

        var o2 = bits.toList()
        var bitPosition = 0
        while (o2.size > 1) {
            val common = o2.rotate().map { it.sum() }.map { if (it >= o2.size - it) 1 else 0 }
            o2 = o2.filter { it[bitPosition] == common[bitPosition] }
            bitPosition++
        }

        var co2 = bits.toList()
        bitPosition = 0
        while (co2.size > 1) {
            val common = co2.rotate().map { it.sum() }.map { if (it >= co2.size - it) 0 else 1 }
            co2 = co2.filter { it[bitPosition] == common[bitPosition] }
            bitPosition++
        }

        val o2Value = o2.first().joinToString("").toInt(2)
        val co2Value = co2.first().joinToString("").toInt(2)

        return o2Value * co2Value;
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03");
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun <T> List<List<T>>.rotate(): List<List<T>> {
    if (isEmpty()) return emptyList()
    return List(first().size) { i ->
        map { it[i] }
    }
}
