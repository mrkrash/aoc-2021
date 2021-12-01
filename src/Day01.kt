fun main() {
    fun part1(input: List<String>): Int {
        return countWithSlidingWindow(input.map { it.toInt() }, 1)
    }

    fun part2(input: List<String>): Int {
        return countWithSlidingWindow(input.map { it.toInt() }, 3)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun countWithSlidingWindow(input: List<Int>, windowSize: Int): Int {
    var last = 0
    var increasedCount = 0
    input.drop(windowSize).forEachIndexed { index, s ->
        val current = (0 until windowSize).sumOf {
            input[index + it]
        }
        if (last < current) {
            increasedCount++
        }
        last = current
    }
    return increasedCount
}