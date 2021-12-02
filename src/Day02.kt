fun main() {
    fun part1(input: List<String>): Int {
        return finalDepth(input)
    }

    fun part2(input: List<String>): Int {
        return finalDepth(input, true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun finalDepth(input: List<String>, precision: Boolean = false): Int {
    var forward = 0
    var depth = 0
    var aim = 0
    input.forEach {
        val move = it.split(" ")
        when (move[0]) {
            "up" -> {
                if (precision) {
                    aim -= move[1].toInt()
                } else {
                    depth -= move[1].toInt()
                }
            }
            "down" -> {
                if (precision) {
                    aim += move[1].toInt()
                } else {
                    depth += move[1].toInt()
                }
            }
            else -> {
                forward += move[1].toInt()
                if (precision) {
                    depth += aim * move[1].toInt()
                }
            }
        }
    }
    return forward * depth
}