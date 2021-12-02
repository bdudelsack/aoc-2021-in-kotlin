fun main() {
    fun part1(input: List<String>): Int {
        var pos = 0
        var depth = 0

        input.forEach {
            val (op, arg) = it.split(" ")
            when (op) {
                "forward" -> pos += arg.toInt()
                "down" -> depth += arg.toInt()
                "up" -> depth -= arg.toInt()
            }
        }

        return pos * depth
    }

    fun part2(input: List<String>): Int {
        var pos = 0
        var aim = 0
        var depth = 0

        input.forEach {
            val (op, arg) = it.split(" ")
            when (op) {
                "forward" -> {
                    val n = arg.toInt()
                    pos += n
                    depth += n * aim
                }
                "down" -> aim += arg.toInt()
                "up" -> aim -= arg.toInt()
            }
        }

        return pos * depth
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}
