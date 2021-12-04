data class Cell(val value: Int, val hit: Boolean = false)

data class BingoBoard(var data: List<List<Cell>>, var winningIndex: Int = -1) {
    fun mark(value: Int) {
        data = data.map { row ->
            row.map {
                if (it.value == value) Cell(value, true) else it
            }
        }
    }

    fun isWinning(): Boolean {
        data.forEach {
            if (it.all { it.hit }) return true
        }

        data.rotate().forEach {
            if (it.all { it.hit }) return true
        }

        return false
    }

    fun sumUnmarked(): Int {
        return data.sumOf { row ->
            row.filter { !it.hit }.sumOf { it.value }
        }
    }

    companion object {
        fun fromLines(lines: List<String>): BingoBoard {
            return BingoBoard(buildList {
                for (line in lines) {
                    val parts = line.trim().split("[ ]+".toRegex())
                    add(parts.map { Cell(it.toInt()) })
                }
            })
        }
    }
}

fun main() {

    fun part1(lines: List<String>): Int {
        val numbers = lines.first().split(",").map { it.toInt() }
        val boards = lines.subList(2, lines.size).filter { it.isNotEmpty() }.chunked(5).map { BingoBoard.fromLines(it) }

        numbers.forEach { value ->
            boards.forEach { board ->
                board.mark(value)
                if (board.isWinning()) {
                    return board.sumUnmarked() * value
                }
            }
        }

        return -1
    }

    fun part2(lines: List<String>): Int {
        val numbers = lines.first().split(",").map { it.toInt() }
        val boards = lines.subList(2, lines.size).filter { it.isNotEmpty() }.chunked(5).map { BingoBoard.fromLines(it) }

        for ((index, number) in numbers.withIndex()) {
            for (board in boards) {
                if (board.winningIndex == -1) {
                    board.mark(number)
                    if (board.isWinning()) {
                        board.winningIndex = index;
                    }
                }
            }
        }

        val lastBoard = boards.maxByOrNull { it.winningIndex }!!
        return lastBoard.sumUnmarked() * numbers[lastBoard.winningIndex]
    }

    val inputTest = readInput("Day04_test")
    check(part1(inputTest) == 4512)
    check(part2(inputTest) == 1924)

    val input = readInput("Day04")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")

}
