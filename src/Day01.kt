fun main() {
    fun part1(input: List<Int>): Int {
        return input.zipWithNext().count { (a, b) ->  b > a }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3, 1, false).map { it.sum() }.zipWithNext().count { (a, b) ->  b > a }
    }

    val testInput= readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
