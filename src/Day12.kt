fun String.isLowercase() = toCharArray().all { it.isLowerCase() }

class Labyrinth {
    private val edges: MutableMap<String, MutableList<String>> = mutableMapOf()

    fun read(lines: List<String>) {
        lines.forEach { line ->
            val (n1, n2) = line.split("-")

            if (!edges.containsKey(n1)) {
                edges[n1] = mutableListOf(n2)
            } else {
                edges[n1]!!.add(n2)
            }

            if (!edges.containsKey(n2)) {
                edges[n2] = mutableListOf(n1)
            } else {
                edges[n2]!!.add(n1)
            }
        }
    }

    fun paths(
        node: String,
        path: List<String> = emptyList(),
        res: MutableSet<String> = mutableSetOf()
    ): MutableSet<String> {
        if (node == "end") {
            res.add((path + node).joinToString(","))
        } else {
            edges[node]!!.forEach { target ->
                if (target.isLowercase()) {
                    if (!path.contains(target)) {
                        paths(target, path + listOf(node), res)
                    }
                } else {
                    paths(target, path + listOf(node), res)
                }
            }
        }

        return res
    }

    fun paths2(node: String, path: List<String> = emptyList(), res: MutableSet<String> = mutableSetOf()): Set<String> {
        val newPath = path + node
        if (node == "end") {
            res.add(newPath.joinToString(","))
        } else {
            edges[node]!!.forEach { target ->
                if (target.isLowercase()) {
                    if (!newPath.contains(target)) {
                        paths2(target, newPath, res)
                    } else if (target != "start" && target != "end") {
                        val visits = newPath.filter { it.isLowercase() }.groupBy { it }.mapValues { (_, v) -> v.size }
                        if (visits.entries.none { it.value > 1 }) {
                            paths2(target, newPath, res)
                        }
                    }
                } else {
                    paths2(target, newPath, res)
                }
            }
        }

        return res
    }
}


fun main() {
    fun part1(lines: List<String>): Int {
        val labyrinth = Labyrinth().apply { read(lines) }
        val paths = labyrinth.paths("start")

        return paths.size
    }

    fun part2(lines: List<String>): Int {
        val labyrinth = Labyrinth().apply { read(lines) }
        val paths = labyrinth.paths2("start")

        return paths.size
    }

    val simpleTestInput = readInput("Day12_simpletest")
    check(part1(simpleTestInput) == 10)
    check(part2(simpleTestInput) == 36)

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 19)
    check(part2(testInput) == 103)

    val largeTestInput = readInput("Day12_largetest")
    check(part1(largeTestInput) == 226)
    check(part2(largeTestInput) == 3509)

    val input = readInput("Day12")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
