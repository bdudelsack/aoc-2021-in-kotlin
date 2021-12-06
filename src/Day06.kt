fun main() {

    fun solution(input: List<String>, n: Int = 80): Long {
        val life = LongArray(9) { 0L }

        input.first().split(",").forEach { life[it.toInt()]++ }
        repeat(n) {
            val count = life[0]

            for (i in (0 until life.size - 1)) {
                life[i] = life[i + 1]
            }

            life[6] += count
            life[8] = count
        }

        return life.sum()
    }

    val testInput = readInput("Day06_test")
    check(solution(testInput) == 5934L)
    check(solution(testInput, 256) == 26984457539L)

    val input = readInput("Day06")
    println("Part 1: ${solution(input)}")
    println("Part 2: ${solution(input, 256)}")
}
