fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) {
            acc, row ->
                acc + row.split(" | ")[1]
                    .split(" ")
                    .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }

        }
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08/Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08/Day08")
    println(part1(input))
//    println(part2(input))
}