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
        return input.fold(0) {
            acc, row ->
                acc + Decoder().instruct(row).display()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08/Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08/Day08")
    println(part1(input))
    println(part2(input))
}

data class Decoder(
    var one : String ?= null,
    var two : String ?= null,
    var three : String ?= null,
    var four : String ?= null,
    var five : String ?= null,
    var six : String ?= null,
    var seven : String ?= null,
    var eight : String ?= null,
    var nine : String ?= null,
    var zero : String ?= null,

    var bus : List<String> ?= null,
    var value : List<String> ?= null
) {
    private fun decode(sequence: String) {
        val sequenceOrdered = sequence.toCharArray().sorted().joinToString("")
        when (sequenceOrdered.length) {
            2 -> one = sequenceOrdered
            3 -> seven = sequenceOrdered
            4 -> four = sequenceOrdered
            6 -> if (sequenceOrdered.contains(four!![0]) && sequenceOrdered.contains(four!![1]) &&
                sequenceOrdered.contains(four!![2]) && sequenceOrdered.contains(four!![3])) {
                    nine = sequenceOrdered
                } else if (sequenceOrdered.contains(seven!![0]) && sequenceOrdered.contains(seven!![1]) &&
                sequenceOrdered.contains(seven!![2])) {
                    zero = sequenceOrdered
                } else six = sequenceOrdered
            7 -> eight = sequenceOrdered
            else -> if (sequenceOrdered.contains(seven!![0]) && sequenceOrdered.contains(seven!![1]) &&
                sequenceOrdered.contains(seven!![2])) {
                    three = sequenceOrdered
                } else if (six!!.contains(sequenceOrdered[0]) && six!!.contains(sequenceOrdered[1]) &&
                six!!.contains(sequenceOrdered[2]) && six!!.contains(sequenceOrdered[3]) &&
                six!!.contains(sequenceOrdered[4])) {
                    five = sequenceOrdered
                } else two = sequenceOrdered
        }
    }
    fun instruct(input: String): Decoder {
        bus = input.split(" | ")[0].split(" ")
        value = input.split(" | ")[1].split(" ")
        bus!!.filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7  }.forEach { decode(it) }
        bus!!.filter { it.length == 6  }.forEach { decode(it) }
        bus!!.filter { it.length == 5  }.forEach { decode(it) }
        return this
    }
    private fun convert(input: String) : Int {
        return when(input.toCharArray().sorted().joinToString("")) {
            one -> 1
            two -> 2
            three -> 3
            four -> 4
            five -> 5
            six -> 6
            seven -> 7
            eight -> 8
            nine -> 9
            else -> 0
        }
    }
    fun display() : Int {
        return convert(value!![0]) * 1000 + convert(value!![1]) * 100 + convert(value!![2]) *10 + convert(value!![3])
    }
}