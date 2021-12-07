import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val crabs =input.first().split(",").map { it.toInt() }
        var least : Int = 100000000
        crabs.forEach { crab ->
            val scarto = crabs.fold(0) {
                acc, it -> acc + abs(it - crab)
            }
            least = minOf(least, scarto)
        }
        return least
    }

    fun part2(input: List<String>): Int {
        val crabs = input.first().split(",").map { it.toInt() }.toMutableList()
        crabs.sort()
        var least : Int = 100000000

        for (i in crabs.first() until crabs.last()) {
            var scarto = crabs.fold(0) {
                    acc, it -> acc + sommatoria(abs(it - i))
            }
            least = minOf(least, scarto)
        }

        return least
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07/Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07/Day07")
    println(part1(input))
    println(part2(input))
}

fun sommatoria(n: Int) : Int {
    var sommatoria = 0
    for (i in 0 until n +1) {
        sommatoria += i
    }
    return sommatoria
}