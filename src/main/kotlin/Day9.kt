import java.io.File

fun main() {

    data class Basin(
        val x: Int,
        val y: Int,
        val depth: Int,
        val risk: Int
    )

    class Map(
        private val input: Array<IntArray>
    ) {

        val localBasinsList = mutableListOf<Basin>()

        fun calculateBasins() {
            localBasinsList.clear()
            val width = input.first().size
            val height = input.size
            for (i in 0 until height) {
                for (j in 0 until width) {
                    val left = input.getOrNull(i)?.getOrNull(j - 1)
                    val top = input.getOrNull(i - 1)?.getOrNull(j)
                    val right = input.getOrNull(i)?.getOrNull(j + 1)
                    val bottom = input.getOrNull(i + 1)?.getOrNull(j)
                    val center = input[i][j]
                    val neighbours = listOfNotNull(left, top, right, bottom)
                    var isLocalMin = true
                    neighbours.forEach {
                        if (center >= it) {
                            isLocalMin = false
                        }
                    }
                    if (isLocalMin) {
                        localBasinsList.add(Basin(i, j, center, 1 + center))
                    }
                }
            }
        }

        fun getRiskSum() = localBasinsList.sumOf { it.risk }
    }

    fun part1(): Int {
        val input = File("./src/main/resources/day_9/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .map { line -> line.map { it.digitToInt() } }
            .map { it.toIntArray() }
            .toTypedArray()
        val map = Map(input)
        map.calculateBasins()
        return map.getRiskSum()
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_9/input.txt")
            .readLines()
        return 0
    }
    println(part1())
    println(part2())
}