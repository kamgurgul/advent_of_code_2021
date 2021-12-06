import java.io.File

class LenternfishSimulator(initialLenternfishList: List<Int>) {

    private val shoalMap = mutableMapOf<Int, Long>()

    init {
        for (i in 0..8) {
            shoalMap[i] = initialLenternfishList.count { it == i }.toLong()
        }
    }

    fun simulate(length: Int): Long {
        for (day in 1..length) {
            val newFishCount = shoalMap.getValue(0)
            for (key in 1..8) {
                shoalMap[key - 1] = shoalMap.getValue(key)
            }
            shoalMap[6] = shoalMap.getValue(6) + newFishCount
            shoalMap[8] = newFishCount
        }
        return shoalMap.values.sum()
    }
}

fun main() {
    fun part1(): Long {
        val input = File("./src/main/resources/day_6/input.txt")
            .readLines()
            .first()
            .split(",")
            .map { it.toInt() }
        val simulator = LenternfishSimulator(input)
        return simulator.simulate(80)
    }

    fun part2(): Long {
        val input = File("./src/main/resources/day_6/input.txt")
            .readLines()
            .first()
            .split(",")
            .map { it.toInt() }
        val simulator = LenternfishSimulator(input)
        return simulator.simulate(256)
    }
    println(part1())
    println(part2())
}