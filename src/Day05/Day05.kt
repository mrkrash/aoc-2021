import java.lang.Math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val coordinates = retrieveCoordinates(input)
        var myMap = MyMap(generateMap(1000))
        coordinates.forEach { coordinate ->
            myMap = markCoordinateHVOnMap(coordinate, myMap)
        }

        return myMap.overlap()
    }

    fun part2(input: List<String>): Int {
        val coordinates = retrieveCoordinates(input)
        var myMap = MyMap(generateMap(1000))
        coordinates.forEach { coordinate ->
            myMap = markCoordinateHVOnMap(coordinate, myMap)
            if (abs(coordinate.I.x - coordinate.II.y) == abs(coordinate.I.y - coordinate.II.x) ||
                abs(coordinate.I.x - coordinate.II.x) == abs(coordinate.I.y - coordinate.II.y)) {
                myMap = markCoordinateD45OnMap(coordinate, myMap)
            }
        }

        return myMap.overlap()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05/Day05")
    println(part1(input))
    println(part2(input))
}

data class Point(
    val x : Int,
    val y : Int
)
data class Coordinate(
    val I : Point,
    val II : Point
)
data class MyMap(
    val points: ArrayList<ArrayList<Int>>
) {
    fun overlap() : Int {
        return points.fold(0) {
            sum, row -> sum + row.filter { it >= 2 }.fold(0) { sum, _ -> sum +1}
        }
    }
}

fun generateMap(size: Int): ArrayList<ArrayList<Int>> {
    var emptyPoints = arrayListOf<ArrayList<Int>>()
    for (i in 0 until size) {
        var array = arrayListOf<Int>()
        for (j in 0 until size) {
            array += 0
        }
        emptyPoints += array
    }
    return emptyPoints
}

fun retrieveCoordinates(input: List<String>) : ArrayList<Coordinate> {
    val coordinates = ArrayList<Coordinate>()
    input.forEach { it2 ->
        it2.split(" -> ", ",").let {
        coordinates.add(Coordinate(
            I = Point( x = it[0].toInt(), y = it[1].toInt()),
            II = Point( x = it[2].toInt(), y = it[3].toInt()),
        ))
    }}
    return coordinates
}

fun markCoordinateHVOnMap(coordinate: Coordinate, map: MyMap) : MyMap {
    if (coordinate.I.x == coordinate.II.x) {
        if (coordinate.I.y <= coordinate.II.y) {
            for (i in coordinate.I.y .. coordinate.II.y) {
                map.points[i][coordinate.I.x] += 1
            }
        } else {
            for (i in coordinate.II.y .. coordinate.I.y) {
                map.points[i][coordinate.I.x] += 1
            }
        }
    }
    if (coordinate.I.y == coordinate.II.y) {
        if (coordinate.I.x <= coordinate.II.x) {
            for (i in coordinate.I.x .. coordinate.II.x) {
                map.points[coordinate.I.y][i] += 1
            }
        } else {
            for (i in coordinate.II.x .. coordinate.I.x) {
                map.points[coordinate.I.y][i] += 1
            }
        }
    }
    return map
}

fun markCoordinateD45OnMap(coordinate: Coordinate, map: MyMap) : MyMap {
    map.points[coordinate.I.y][coordinate.I.x] += 1
    if (coordinate.I == coordinate.II) {
        return map
    }
    var coord_x = if (coordinate.I.x <= coordinate.II.x) {
        coordinate.I.x + 1
    } else {
        coordinate.I.x - 1
    }
    var coord_y = if (coordinate.I.y <= coordinate.II.y) {
        coordinate.I.y + 1
    } else {
        coordinate.I.y - 1
    }
    return markCoordinateD45OnMap(Coordinate(Point(coord_x, coord_y), coordinate.II), map)
}