fun main() {
    fun part1(input: List<String>): Int {
        val zeusLifeCycle = ZeusLifeCycle()
        input.first().split(",").map { it.toInt() }.forEach {
            when (it) {
                0 -> zeusLifeCycle.zero += 1
                1 -> zeusLifeCycle.one += 1
                2 -> zeusLifeCycle.two += 1
                3 -> zeusLifeCycle.three += 1
                4 -> zeusLifeCycle.four += 1
                5 -> zeusLifeCycle.five += 1
                6 -> zeusLifeCycle.six += 1
                7 -> zeusLifeCycle.seven += 1
                else -> zeusLifeCycle.eight += 1
            }
        }
        return lifeCycle(80, zeusLifeCycle).toInt()
    }

    fun part2(input: List<String>): Long {
        val zeusLifeCycle = ZeusLifeCycle()
        input.first().split(",").map { it.toInt() }.forEach {
            when (it) {
                0 -> zeusLifeCycle.zero += 1
                1 -> zeusLifeCycle.one += 1
                2 -> zeusLifeCycle.two += 1
                3 -> zeusLifeCycle.three += 1
                4 -> zeusLifeCycle.four += 1
                5 -> zeusLifeCycle.five += 1
                6 -> zeusLifeCycle.six += 1
                7 -> zeusLifeCycle.seven += 1
                else -> zeusLifeCycle.eight += 1
            }
        }
        return lifeCycle(256, zeusLifeCycle)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06/Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06/Day06")
    println(part1(input))
    println(part2(input))
}

data class ZeusLifeCycle(
    var zero : Long = 0,
    var one : Long = 0,
    var two : Long = 0,
    var three : Long = 0,
    var four : Long = 0,
    var five : Long = 0,
    var six : Long = 0,
    var seven : Long = 0,
    var eight : Long = 0,
) {
    fun aNewDay() {
        val temp = zero
        zero = one
        one = two
        two = three
        three = four
        four = five
        five = six
        six = seven + temp
        seven = eight
        eight = temp
    }
    fun fauna() : Long {
        return zero + one + two + three + four + five + six + seven + eight
    }
}

fun lifeCycle(days: Int, zeusLifeCycle: ZeusLifeCycle) : Long {
    if (days > 0) {
        zeusLifeCycle.aNewDay()
        lifeCycle(days -1, zeusLifeCycle)
    }
    return zeusLifeCycle.fauna()
}