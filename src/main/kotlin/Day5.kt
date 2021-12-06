import java.io.File
import kotlin.math.abs

data class Coords(
    val x: Int,
    val y: Int
)

data class VentLine(
    val from: Coords,
    val to: Coords
) {

    fun isVerticalLine() = from.y == to.y

    fun isHorizontalLine() = from.x == to.x

    fun toList() = buildList {
        add(from.x)
        add(from.y)
        add(to.x)
        add(to.y)
    }

    companion object {

        fun parse(input: String): VentLine {
            val numbers = input.split(",", " -> ")
            val from = Coords(numbers[0].toInt(), numbers[1].toInt())
            val to = Coords(numbers[2].toInt(), numbers[3].toInt())
            return VentLine(from, to)
        }
    }
}

class VentsMap(
    private val ventLines: List<VentLine>
) {

    private val map: Array<IntArray>

    init {
        val mapSize = calculateMapSize()
        map = Array(mapSize) { IntArray(mapSize) { 0 } }
    }

    fun draw(withDiagonal: Boolean = false) {
        ventLines.forEach {
            if (it.isHorizontalLine()) {
                if (it.from.y >= it.to.y) {
                    for (y in it.from.y downTo it.to.y) {
                        markSpot(it.from.x, y)
                    }
                } else {
                    for (y in it.from.y..it.to.y) {
                        markSpot(it.from.x, y)
                    }
                }
            } else if (it.isVerticalLine()) {
                if (it.from.x >= it.to.x) {
                    for (x in it.from.x downTo it.to.x) {
                        markSpot(x, it.from.y)
                    }
                } else {
                    for (x in it.from.x..it.to.x) {
                        markSpot(x, it.from.y)
                    }
                }
            } else if (withDiagonal) {
                val leftTopDir = it.from.x > it.to.x && it.from.y > it.to.y
                val rightTopDir = it.from.x < it.to.x && it.from.y > it.to.y
                val leftBottomDir = it.from.x > it.to.x && it.from.y < it.to.y
                val length = abs(it.from.x - it.to.x)
                for (counter in 0..length) {
                    when {
                        leftTopDir -> markSpot(it.from.x - counter, it.from.y - counter)
                        rightTopDir -> markSpot(it.from.x + counter, it.from.y - counter)
                        leftBottomDir -> markSpot(it.from.x - counter, it.from.y + counter)
                        else -> markSpot(it.from.x + counter, it.from.y + counter)
                    }
                }
            }
        }
    }

    fun getDangerSpotsAmount() = map.sumOf { row -> row.count { it > 1 } }

    private fun markSpot(x: Int, y: Int) {
        map[y][x] = map[y][x] + 1
    }

    private fun calculateMapSize(): Int {
        val allCoords = buildList { ventLines.forEach { addAll(it.toList()) } }
        return allCoords.maxOf { it } + 1
    }
}

fun main() {
    fun part1(): Int {
        val input = File("./src/main/resources/day_5/input.txt")
            .readLines()
            .filterNot { it.isEmpty() }
            .map { VentLine.parse(it) }

        val ventsMap = VentsMap(input)
        ventsMap.draw()

        return ventsMap.getDangerSpotsAmount()
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_5/input.txt")
            .readLines()
            .filterNot { it.isEmpty() }
            .map { VentLine.parse(it) }

        val ventsMap = VentsMap(input)
        ventsMap.draw(withDiagonal = true)

        return ventsMap.getDangerSpotsAmount()
    }
    println(part1())
    println(part2())
}