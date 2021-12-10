fun main() {
    fun part1(input: List<String>): Int {
        var lows = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                val el = listOfNotNull(
                    row.getOrNull(x - 1),
                    row.getOrNull(x + 1),
                    input.getOrNull(y - 1)?.get(x),
                    input.getOrNull(y + 1)?.get(x),
                )
                if (c < el.minOrNull()!!) lows += 1 + (c - '0')
            }
        }
        return lows
    }

    fun part2(input: List<String>): Int {
        var basis = arrayListOf<Int>()
        var visited = mutableListOf<Pair<Int, Int>>()

        fun List<String>.neighboursOf(x: Int, y: Int) = listOf(
            x - 1 to y, x + 1 to y, x to y - 1, x to y + 1
        ).filter { (i, j) -> i in this[0].indices && j in indices }

        fun basi(x: Int, y: Int): Int {
            if (x to y in visited || input[y][x] == '9') return 0
            visited.add(x to y)
            return 1 + input.neighboursOf(x, y).sumOf { (i, j) -> basi(i, j) }
        }
        for (y in 0 until input.size) {
            for (x in 0 until input[0].length) {
                if (input[y][x] < input.neighboursOf(x, y).minOf { (i, j) -> input[j][i] } ) {
                    basis.add(basi(x, y))
                }
            }
        }
        basis.sortDescending()
        return basis[0] * basis[1] * basis[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09/Day09")
    println(part1(input))
    println(part2(input))
}
