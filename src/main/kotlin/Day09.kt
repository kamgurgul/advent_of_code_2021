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

        private val localBasinsList = mutableListOf<Basin>()
        private val visitedLocations = mutableSetOf<Pair<Int, Int>>()

        fun calculateBasins() {
            val width = input.first().size
            val height = input.size
            for (i in 0 until height) {
                for (j in 0 until width) {
                    val left = input.getOrNull(i)?.getOrNull(j - 1)
                    val up = input.getOrNull(i - 1)?.getOrNull(j)
                    val right = input.getOrNull(i)?.getOrNull(j + 1)
                    val down = input.getOrNull(i + 1)?.getOrNull(j)
                    val center = input[i][j]
                    val neighbours = listOfNotNull(left, up, right, down)
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

        fun getThreeBiggestBasins(): Int {
            return localBasinsList
                .map {
                    visitedLocations.clear()
                    getBasinSizeRec(it.x, it.y)
                    visitedLocations.size
                }
                .sortedDescending()
                .take(3)
                .reduce { acc, area -> acc * area }
        }

        private fun getBasinSizeRec(i: Int, j: Int) {
            visitedLocations.add(i to j)
            val canGoLeft = (i to j - 1) !in visitedLocations
                    && input.getOrNull(i)?.getOrNull(j - 1)?.let { it < 9 } ?: false
            val canGoUp = (i - 1 to j) !in visitedLocations
                    && input.getOrNull(i - 1)?.getOrNull(j)?.let { it < 9 } ?: false
            val canGoRight = (i to j + 1) !in visitedLocations
                    && input.getOrNull(i)?.getOrNull(j + 1)?.let { it < 9 } ?: false
            val canGoDown = (i + 1 to j) !in visitedLocations
                    && input.getOrNull(i + 1)?.getOrNull(j)?.let { it < 9 } ?: false
            if (canGoLeft) {
                getBasinSizeRec(i, j - 1)
            }
            if (canGoUp) {
                getBasinSizeRec(i - 1, j)
            }
            if (canGoRight) {
                getBasinSizeRec(i, j + 1)
            }
            if (canGoDown) {
                getBasinSizeRec(i + 1, j)
            }
        }
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
            .filter { it.isNotEmpty() }
            .map { line -> line.map { it.digitToInt() } }
            .map { it.toIntArray() }
            .toTypedArray()
        val map = Map(input)
        map.calculateBasins()
        return map.getThreeBiggestBasins()
    }
    println(part1())
    println(part2())
}