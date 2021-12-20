fun main() {
    fun part1(input: List<String>): Int {
        return step(input)
    }

    fun part2(input: List<String>): Int {
        return flashSynchronized(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11/Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11/Day11")
    println(part1(input))
    println(part2(input))
}

fun List<MutableList<Int>>.neighboursOf(x: Int, y: Int) = listOf(
    x - 1 to y, x + 1 to y, x to y - 1, x to y + 1,
    x - 1 to y - 1, x + 1 to y - 1, x - 1 to y + 1, x + 1 to y + 1
).filter { (i, j) -> i in this.indices && j in indices }

fun step(input: List<String>) : Int {
    val a = input.map { it.map { it.digitToInt() }.toMutableList() }
    var flashed = 0
    repeat (100) {
        var hasFlashed = mutableListOf<Pair<Int, Int>>()
        fun add(i : Int, j : Int) {
            if (i to j !in hasFlashed) {
                a[i][j]++
                if (a[i][j] == 10) {
                    hasFlashed.add(i to j)
                    a[i][j] = 0
                    flashed++
                    a.neighboursOf(i, j).forEach { (r, s) ->
                        add(r, s)
                    }
                }
            }
        }
        a.forEachIndexed { y, row ->
            row.forEachIndexed { x, el ->
                add(x, y)
            }
        }
    }
    return flashed
}

fun flashSynchronized(input: List<String>) : Int {
    val a = input.map { it.map { it.digitToInt() }.toMutableList() }
    var syncAt = 1
    while (true) {
        var hasFlashed = mutableListOf<Pair<Int, Int>>()
        fun add(i : Int, j : Int) {
            if (i to j !in hasFlashed) {
                a[i][j]++
                if (a[i][j] == 10) {
                    hasFlashed.add(i to j)
                    a[i][j] = 0
                    a.neighboursOf(i, j).forEach { (r, s) ->
                        add(r, s)
                    }
                }
            }
        }
        a.forEachIndexed { y, row ->
            row.forEachIndexed { x, el ->
                add(x, y)
            }
        }
        if (hasFlashed.size == a.size * a[0].size) {
            break
        }
        syncAt++
    }
    return syncAt
}