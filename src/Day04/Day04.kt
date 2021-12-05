fun main() {
    fun part1(input: List<String>): Int {
        val boards = getBoards(input)
        numberExtracted(input).forEach { value ->
            boards.forEach { board ->
                if (board.checkBoard(value)) {
                    return value * board.sumUnmarked()
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        var boards = getBoards(input)
        var lastNumberCalled : Int? = null
        var lastWinnerBoard : Board? = null
        numberExtracted(input).forEach { value ->
            for (board in boards) {
                if (board.checkBoard(value)) {
                    boards = (boards - board) as MutableList<Board>
                    lastNumberCalled = value
                    lastWinnerBoard = board
                }
            }
        }
        return lastNumberCalled!! * lastWinnerBoard!!.sumUnmarked()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04/Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04/Day04")
    println(part1(input))
    println(part2(input))
}

fun numberExtracted(input: List<String>) : List<Int> {
    return input.first().split(",").map { it.toInt() }
}

fun getBoards(input: List<String>): MutableList<Board> {
    val boards = mutableListOf<Board>()
    input.drop(1).chunked(6).forEachIndexed { _, s ->
        boards.add(
            Board(
                row1 = s[1].split("  ", " ").filter { it.isNotBlank() }.map { BoardField(value = it.toInt() )},
                row2 = s[2].split("  ", " ").filter { it.isNotBlank() }.map { BoardField(value = it.toInt() )},
                row3 = s[3].split("  ", " ").filter { it.isNotBlank() }.map { BoardField(value = it.toInt() )},
                row4 = s[4].split("  ", " ").filter { it.isNotBlank() }.map { BoardField(value = it.toInt() )},
                row5 = s[5].split("  ", " ").filter { it.isNotBlank() }.map { BoardField(value = it.toInt() )},
            )
        )
    }
    return boards
}

data class BoardField(
    val value: Int,
    var checked: Boolean = false
)
data class Board(
    var row1: List<BoardField>,
    var row2: List<BoardField>,
    var row3: List<BoardField>,
    var row4: List<BoardField>,
    var row5: List<BoardField>
) {
    private fun checkColumn(idx: Int): Boolean {
        return row1[idx].checked && row2[idx].checked && row3[idx].checked && row4[idx].checked &&
                row5[idx].checked
    }
    private fun checkRow(row: List<BoardField>) : Boolean {
        return row.all { it.checked }
    }
    private fun markField(value: Int) {
        markFieldInRow(value, row1)
        markFieldInRow(value, row2)
        markFieldInRow(value, row3)
        markFieldInRow(value, row4)
        markFieldInRow(value, row5)
    }
    private fun markFieldInRow(value: Int, row: List<BoardField>) {
        for (_row in row) {
            if (_row.value == value) {
                _row.checked = true
            }
        }
    }
    fun checkBoard(value: Int) : Boolean {
        markField(value)
        return checkRow(row1) || checkRow(row2) || checkRow(row3) || checkRow(row4) || checkRow(row5) ||
                checkColumn(0) || checkColumn(1) || checkColumn(2) || checkColumn(3) ||
                checkColumn(4)
    }
    fun sumUnmarked(): Int {
        return row1.filter { !it.checked }.sumOf { it.value } + row2.filter { !it.checked }.sumOf { it.value } +
                row3.filter { !it.checked }.sumOf { it.value } + row4.filter { !it.checked }.sumOf { it.value } +
                row5.filter { !it.checked }.sumOf { it.value }
    }
}