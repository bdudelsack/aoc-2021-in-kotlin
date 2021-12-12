fun main() {
    fun closingChar(c: Char) = when (c) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> throw RuntimeException("Unknown opening char: $c")
    }

    fun scoreChar(c: Char) = when (c) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw RuntimeException("Unknown closing char: $c")
    }

    fun scoreCompletionChar(c: Char) = when (c) {
        ')' -> 1L
        ']' -> 2L
        '}' -> 3L
        '>' -> 4L
        else -> throw RuntimeException("Unknown closing char: $c")
    }

    fun scoreCompletion(completion: String) = completion.toCharArray().fold(0L) { acc, c ->
        acc * 5L + scoreCompletionChar(c)
    }

    fun part1(lines: List<String>): Int {
        val chars: List<Int> = lines.map { line ->
            val stack = ArrayDeque<Char>()
            for (c in line.toCharArray()) {
                if (c in setOf('(', '[', '{', '<')) {
                    stack.addLast(c)
                } else {
                    val target = closingChar(stack.removeLast())
                    if (target != c) {
                        return@map scoreChar(c)
                    }
                }
            }
            0
        }

        return chars.sum()
    }

    fun part2(lines: List<String>): Long {
        val incomplete: List<Long?> = lines.map { line ->
            val stack = ArrayDeque<Char>()
            for (c in line.toCharArray()) {
                if (c in setOf('(', '[', '{', '<')) {
                    stack.addLast(c)
                } else {
                    val target = closingChar(stack.removeLast())
                    if (target != c) {
                        return@map null
                    }
                }
            }
            scoreCompletion(stack.reversed().map { closingChar(it) }.joinToString(""))
        }.filterNotNull().sorted()


        return incomplete[(incomplete.size / 2)]!!
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
