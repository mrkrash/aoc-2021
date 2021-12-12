fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) {
            acc, row -> run {
                var t = 0
                val lastQueue = arrayListOf<String>()
                for (i in row.indices) {
                    when (row[i].toString()) {
                        ")" -> if (lastQueue.last() != "(") {
                            t = score(row[i].toString())
                            break
                        } else lastQueue.removeLast()
                        "]" -> if (lastQueue.last() != "[") {
                            t = score(row[i].toString())
                            break
                        } else lastQueue.removeLast()
                        "}" -> if (lastQueue.last() != "{") {
                            t = score(row[i].toString())
                            break
                        } else lastQueue.removeLast()
                        ">" -> if (lastQueue.last() != "<") {
                            t = score(row[i].toString())
                            break
                        } else lastQueue.removeLast()
                        else -> lastQueue.add(row[i].toString())
                    }
                }
                acc + t
            }
        }
    }

    fun part2(input: List<String>): Long {
        val middles = arrayListOf<Long>()
        input.forEach {
                row -> run {
                    var valid = true
                    val lastQueue = arrayListOf<String>()
                    for (i in row.indices) {
                        when (row[i].toString()) {
                            ")" -> if (lastQueue.last() != "(") {
                                valid = false
                                break
                            } else lastQueue.removeLast()
                            "]" -> if (lastQueue.last() != "[") {
                                valid = false
                                break
                            } else lastQueue.removeLast()
                            "}" -> if (lastQueue.last() != "{") {
                                valid = false
                                break
                            } else lastQueue.removeLast()
                            ">" -> if (lastQueue.last() != "<") {
                                valid = false
                                break
                            } else lastQueue.removeLast()
                            else -> lastQueue.add(row[i].toString())
                        }
                    }
                    if (valid) {
                        middles.add(lastQueue.foldRight(0) {
                            ch, acc ->
                            acc * 5 + mscore(ch)
                        })
                    }
                }
        }
        println(middles)
        return middles.sorted()[middles.size /2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10/Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10/Day10")
    println(part1(input))
    println(part2(input))
}

fun score(illegalChar: String) : Int {
    return when(illegalChar) {
        ")" -> 3
        "]" -> 57
        "}" -> 1197
        else -> 25137
    }
}

fun mscore(charToClose: String) : Int {
    return when(charToClose) {
        "(" -> 1
        "[" -> 2
        "{" -> 3
        else -> 4
    }
}