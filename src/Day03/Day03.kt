import kotlin.math.pow

var gammaEpsilonRates: GammaEpsilonRates ?= null

fun main() {
    fun part1(input: List<String>): Int {
        gammaEpsilonRates = powerConsumption(input)
        return gammaEpsilonRates.let { binaryToInt(it!!.epsilon!!) * binaryToInt(it!!.gamma!!) }
    }

    fun part2(input: List<String>): Int {
        return lifeSupportRatings(input, gammaEpsilonRates!!)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03/Day03")
    println(part1(input))
    println(part2(input))
}

data class GammaEpsilonRates (
    val epsilon: Array<Int> ?= null,
    val gamma: Array<Int> ?= null
)

fun powerConsumption(input: List<String>): GammaEpsilonRates {
    val length = input.first().length
    val gamma_rate_binary = Array(length) { 0 }
    val epsilon_rate_binary = Array(length) { 1 }
    val count = Array(length) { 0 }
    input.forEach {
        for (idx in it.indices) {
            count[idx] += it.slice(idx..idx).toInt()
        }
    }
    count.forEachIndexed { index, i -> if (i > (input.size /2)) {
        gamma_rate_binary[index] = 1
        epsilon_rate_binary[index] = 0
        }
    }

    return GammaEpsilonRates(gamma = gamma_rate_binary, epsilon =  epsilon_rate_binary)
}

fun lifeSupportRatings(input: List<String>, gammaEpsilonRates: GammaEpsilonRates) : Int {
    val oxygen = getRatingValue(input, gammaEpsilonRates.gamma!![0].toString(), 0)
    val co2 = getRatingValue(input, gammaEpsilonRates.epsilon!![0].toString(), 0, true)
    return binaryToInt(Array(oxygen.length) { oxygen[it].toString().toInt()}) *
            binaryToInt(Array(co2.length) { co2[it].toString().toInt()})
}

fun getRatingValue(input: List<String>, criteria: String, idx: Int, inverted: Boolean = false) : String {
    return input.filter { it.slice(idx .. idx) == criteria }.let {
        if (it.size == 1) {
            return it.first()
        } else {
            getRatingValue(it, getMoreBit(it, idx +1, inverted), idx +1, inverted)
        }
    }
}

fun getMoreBit(input: List<String>, idx: Int, inverted: Boolean) : String {
    val length = input.first().length
    val count = Array(length) { 0 }
    input.forEach {
        for (idx in it.indices) {
            when (it.slice(idx..idx)) {
                "0" -> count[idx] -= 1
                else -> count[idx] += 1
            }
        }
    }

    return when (inverted) {
        true -> if (count[idx] >= 0) "0" else "1"
        else -> if (count[idx] >= 0) "1" else "0"
    }
}

fun binaryToInt(input: Array<Int>): Int {
    return input.foldRightIndexed(0) {
            idx, element, sum -> sum + (element * 2.0.pow((input.size - idx -1).toDouble())).toInt()
    }
}