fun main() {
    fun part1(lines: List<String>): Int {

        val inputs = lines.map { line ->
            val (first, second) = line.split(" | ").map { it.trim() }
            Pair(first, second)
        }

        return inputs.sumOf { (_, second) ->
            var count = 0
            val words = second.split(" ")

            count += words.count { it.length == 2 }
            count += words.count { it.length == 3 }
            count += words.count { it.length == 4 }
            count += words.count { it.length == 7 }

            count
        }
    }

    fun part2(lines: List<String>): Int {
        val inputs = lines.map { line ->
            val (first, second) = line.split(" | ").map { it.trim() }
            Pair(first.split(" ").map { it.toSet() }, second.split(" ").map { it.toSet() })
        }

        return inputs.sumOf { (input, output) ->
            val solution: MutableMap<Int, Set<Char>> = mutableMapOf(
                1 to input.first { it.size == 2 },
                4 to input.first { it.size == 4 },
                7 to input.first { it.size == 3 },
                8 to input.first { it.size == 7 }
            )

            solution[3] = input.first { it.size == 5 && it.intersect(solution[1]!!).size == 2 }
            solution[9] = input.first { it.size == 6 && it == solution[3]!!.union(solution[4]!!) }
            solution[5] = input.first { it.size == 5 && it.intersect(solution[9]!!).size == 5 && it != solution[3]!! }
            solution[6] = input.first { it.size == 6 && it.intersect(solution[5]!!).size == 5 && it != solution[9]!! }
            solution[2] = input.first { it.size == 5 && it.intersect(solution[3]!!).size == 4 && it != solution[5]!! }
            solution[0] =
                input.first { it.size == 6 && it.intersect(solution[8]!!).size == 6 && it != solution[9]!! && it != solution[6]!! }

            val res = output.map { word ->
                solution.entries.find { word == it.value }!!.key
            }

            res.joinToString("").toInt()
        }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
