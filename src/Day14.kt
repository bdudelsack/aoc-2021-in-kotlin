class Polymerization(lines: List<String>) {
    var template: List<Char>
    val rules: Map<String, String>

    init {
        val iterator = lines.iterator()
        template = iterator.next().toCharArray().toList()
        iterator.next()

        rules = buildMap {
            while (iterator.hasNext()) {
                val line = iterator.next()
                val (pair, el) = line.split(" -> ")
                set(pair, el)
            }
        }
    }

    fun step() {
        template = buildList {
            for (i in 0 until template.size - 1) {
                add(template[i])
                val pair = "${template[i]}${template[i + 1]}"
                if (rules.containsKey(pair)) {
                    add(rules[pair]!!.first())
                }
                if (i == template.size - 2) {
                    add(template[i + 1])
                }
            }
        }
    }

    fun score(depth: Int): Map<Char, Long> {
        val counts = template.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
        val pairCounts = template.windowed(2, 1).map { it.joinToString("") }.groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }.toMutableMap()

        repeat(depth) {
            val tempMap = mutableMapOf<String, Long>()
            pairCounts.forEach { (k, v) ->
                rules[k]?.let { el ->
                    val c = el.first()
                    val firstPair = k.substring(0, 1) + el
                    val secondPair = el + k.substring(1, 2)

                    counts[c] = (counts[c] ?: 0L) + v
                    tempMap[k] = (tempMap[k] ?: 0L) - v
                    tempMap[firstPair] = (tempMap[firstPair] ?: 0L) + v
                    tempMap[secondPair] = (tempMap[secondPair] ?: 0L) + v
                }
            }
            tempMap.forEach { (k, v) ->
                pairCounts[k] = (pairCounts[k] ?: 0L) + v
            }
        }

        return counts.toMap()
    }
}

fun main() {
    fun part1(lines: List<String>): Long {
        val p = Polymerization(lines)

        val counts = p.score(10)

        return counts.maxOf { it.value } - counts.minOf { it.value }
    }

    fun part2(lines: List<String>): Long {
        val p = Polymerization(lines)

        val counts = p.score(40)

        return counts.maxOf { it.value } - counts.minOf { it.value }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
