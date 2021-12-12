import com.github.mm.coloredconsole.colored

class OctopusMap(lines: List<String>) {
    var totalFlashes: Int = 0

    private var data: Array<IntArray> = Array(lines.size) { n ->
        lines[n].toCharArray().map { it.digitToInt() }.toIntArray()
    }

    fun print() {
        colored {
            repeat(data.size) { lineIndex ->
                data[lineIndex].forEach { v -> print(v.red { it == 0 }) }
                println()
            }
            println()
        }
    }

    fun get(x: Int, y: Int): Int {
        return data[y][x]
    }

    fun set(x: Int, y: Int, value: Int): Int {
        data[y][x] = if (value > 9) 0 else value
        return data[y][x]
    }

    private fun increase(x: Int, y: Int, force: Boolean = false): Int? {
        if (y < 0 || y >= data.size) {
            return null
        }

        if (x < 0 || x >= data.first().size) {
            return null
        }

        val value = get(x, y)

        if (!force && value == 0) {
            return null
        }

        return set(x, y, value + 1)
    }

    private fun forEach(block: (x: Int, y: Int) -> Unit) {
        for (y in data.indices) {
            for (x in 0 until data[y].size) {
                block(x, y)
            }
        }
    }

    private fun flashes(): List<Point> {
        return buildList {
            for (y in data.indices) {
                for (x in 0 until data[y].size) {
                    if (get(x, y) == 0) {
                        add(Point(x, y))
                    }
                }
            }
        }
    }

    fun synchronized(): Boolean {
        return data.all { line ->
            line.all { cell -> cell == 0 }
        }
    }

    fun step() {
        forEach { x, y -> increase(x, y, true) }
        var flashes = flashes()

        while (flashes.isNotEmpty()) {
            totalFlashes += flashes.size
            flashes = buildList {
                flashes.forEach { (x, y) ->
                    val neighbors = listOf(
                        Point(x - 1, y - 1),
                        Point(x - 1, y),
                        Point(x - 1, y + 1),

                        Point(x, y - 1),
                        Point(x, y + 1),

                        Point(x + 1, y - 1),
                        Point(x + 1, y),
                        Point(x + 1, y + 1)
                    )

                    neighbors.forEach {
                        if (increase(it.x, it.y) == 0) {
                            add(it)
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    fun part1(lines: List<String>): Int {
        val map = OctopusMap(lines)

        repeat(100) {
            map.step()
        }

        return map.totalFlashes
    }

    fun part2(lines: List<String>): Int {
        val map = OctopusMap(lines)
        var count = 0

        while (!map.synchronized()) {
            map.step()
            count++
        }

        return count
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
