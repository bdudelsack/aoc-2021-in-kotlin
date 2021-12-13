import com.github.mm.coloredconsole.colored
import java.lang.RuntimeException

class Paper(lines: List<String>) {
    var points: Set<Point> = setOf()
    private val iterator: Iterator<String>

    init {
        iterator = lines.iterator()
        readPoints(iterator)
    }

    fun print() {
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        val field = Array(maxX + 1) {
            BooleanArray(maxY + 1)
        }

        points.forEach { field[it.x][it.y] = true }

        colored {
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    print("██".red { field[x][y] })
                }
                println()
            }
        }
    }

    private fun readPoints(it: Iterator<String>) {
        points = buildSet {
            while (it.hasNext()) {
                val line = it.next()
                if (line.isEmpty()) {
                    break
                }
                val (x, y) = line.split(",").map { it.toInt() }
                add(Point(x, y))
            }
        }
    }

    fun foldY(y: Int) {
        points = points.filter { it.y != y }.map {
            if (it.y > y) {
                it.copy(y = y * 2 - it.y)
            } else {
                it
            }
        }.toSet()
    }

    fun foldX(x: Int) {
        points = points.filter { it.x != x }.map {
            if (it.x > x) {
                it.copy(x = x * 2 - it.x)
            } else {
                it
            }
        }.toSet()
    }

    fun execute(): Boolean {
        if (!iterator.hasNext()) {
            return false
        }

        val line = iterator.next()
        if (!line.startsWith("fold along ")) {
            throw RuntimeException("Unknown command: $line")
        }

        val (axe, value) = line.substring(11).split("=")

        if (axe == "x") {
            foldX(value.toInt())
        } else {
            foldY(value.toInt())
        }

        return true
    }
}

fun main() {
    fun part1(lines: List<String>): Int {

        val paper = Paper(lines)
        paper.execute()

        return paper.points.size
    }

    fun part2(lines: List<String>) {
        val paper = Paper(lines)

        do {
            val res = paper.execute()
        } while (res)

        paper.print()
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println("Part 1: ${part1(input)}")
    part2(input)
}
