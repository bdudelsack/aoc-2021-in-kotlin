import java.util.*
import kotlin.math.abs

fun main() {

    fun median(list: List<Int>): Int {
        val sorted = list.sorted()
        return (sorted[sorted.size / 2] + sorted[(sorted.size - 1) / 2]) / 2
    }

    fun calcFuel(n: Int): Int {
        return (n * n + n) / 2
    }

    fun part1(lines: List<String>): Int {
        val positions = lines.first().split(",").map { it.toInt() }
        val target = median(positions)

        return positions.sumOf { abs(target - it) }
    }

    fun part2(lines: List<String>): Int {
        val positions = lines.first().split(",").map { it.toInt() }
        val max = positions.maxOrNull()!!

        val costs = IntArray(max + 1) { target ->
            positions.sumOf { calcFuel(abs(target - it)) }
        }

        return costs.minOrNull()!!
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")

}
