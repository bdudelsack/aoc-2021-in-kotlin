class Heightmap(lines: List<String>) {
    private val data: Array<IntArray> = Array(lines.size) { n ->
        lines[n].toCharArray().map { it.digitToInt() }.toIntArray()
    }

    fun print() {
        repeat(data.size) {
            println(data[it].joinToString(""))
        }
    }

    fun get(x: Int, y: Int): Int {
        return data[y][x]
    }

    fun get(point: Point) = get(point.x, point.y)

    fun lowestPoints(): List<Point> {
        return buildList {
            for (y in data.indices) {
                for (x in 0 until data[y].size) {
                    val element = get(x, y)
                    val neighbors = buildList {
                        if (x > 0) {
                            add(get(x - 1, y))
                        }

                        if (x < data[y].size - 1) {
                            add(get(x + 1, y))
                        }

                        if (y > 0) {
                            add(get(x, y - 1))
                        }

                        if (y < data.size - 1) {
                            add(get(x, y + 1))
                        }
                    }

                    if (neighbors.minOrNull()!! > element) {
                        add(Point(x, y))
                    }
                }
            }
        }
    }

    fun discoverBasin(x: Int, y: Int) = discoverBasin(Point(x, y))

    fun discoverBasin(location: Point): Set<Point> {
        return buildSet {
            val (x, y) = location
            val value = get(location)

            if (value < 9) {
                add(location)

                if (x > 0 && get(x - 1, y) > value) {
                    addAll(discoverBasin(x - 1, y))
                }

                if (x < data[y].size - 1 && get(x + 1, y) > value) {
                    addAll(discoverBasin(x + 1, y))
                }

                if (y > 0 && get(x, y - 1) > value) {
                    addAll(discoverBasin(x, y - 1))
                }

                if (y < data.size - 1 && get(x, y + 1) > value) {
                    addAll(discoverBasin(x, y + 1))
                }
            }
        }
    }
}


fun main() {

    fun part1(lines: List<String>): Int {
        val heightmap = Heightmap(lines)

        return heightmap.lowestPoints().map { heightmap.get(it) }.sumOf { it + 1 }
    }

    fun part2(lines: List<String>): Int {
        val heightmap = Heightmap(lines)

        val basins = heightmap.lowestPoints().map {
            heightmap.discoverBasin(it).size
        }.sortedDescending().take(3).fold(1) { acc, el -> acc * el }

        return basins
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
