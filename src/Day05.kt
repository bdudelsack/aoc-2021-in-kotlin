import kotlin.math.abs

data class Point(val x: Int, val y: Int) {

    companion object {
        fun fromString(input: String): Point {
            val (x, y) = input.trim().split(",").map { it.toInt() }
            return Point(x, y)
        }
    }
}

data class Line(val start: Point, val end: Point) {
    companion object {
        fun fromString(input: String): Line {
            val (start, end) = input.trim().split("->").map { Point.fromString(it) }
            return Line(start, end)
        }
    }
}

data class VentsMap(val width: Int, val height: Int) {
    private val canvas = MutableList(width) { MutableList(height) { 0 } }

    fun drawLine(line: Line) {
        var (x0, y0) = line.start
        val (x1, y1) = line.end

        val dx = abs(x1 - x0)
        val sx = if (x0 < x1) 1 else -1
        val dy = -abs(y1 - y0)
        val sy = if (y0 < y1) 1 else -1
        var err = dx + dy

        while (true) {
            canvas[x0][y0]++
            if (x0 == x1 && y0 == y1) break
            val e2 = 2 * err

            if (e2 >= dy) {
                err += dy
                x0 += sx
            }

            if (e2 <= dx) {
                err += dx
                y0 += sy
            }
        }
    }

    fun count(): Int {
        return canvas.flatten().count { it > 1 }
    }

    fun printOut() {
        for (y in (0 until height)) {
            for (x in (0 until width)) {
                print("${canvas[x][y]} ")
            }
            println()
        }
    }
}

fun main() {
    fun execute(lines: List<Line>): Int {
        val maxX = lines.maxOf { maxOf(it.start.x, it.end.x) }
        val maxY = lines.maxOf { maxOf(it.start.y, it.end.y) }

        val map = VentsMap(maxX + 1, maxY + 1)
        for (line in lines) {
            map.drawLine(line)
        }

        return map.count()
    }

    fun part1(input: List<String>): Int {
        val lines = input.map { Line.fromString(it) }.filter { it.start.x == it.end.x || it.start.y == it.end.y }

        return execute(lines)
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { Line.fromString(it) }

        return execute(lines)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
