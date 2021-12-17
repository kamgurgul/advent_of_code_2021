import java.io.File

fun main() {

    data class Dumbo(
        val energyLvl: Int,
        val x: Int,
        val y: Int,
        val lastFlashStep: Int = 0
    )

    class DumboMap(input: List<List<Int>>) {

        private val internalMap: Array<Array<Dumbo>>
        private val width: Int
        private val height: Int

        private var flashes = 0

        init {
            width = input.first().size
            height = input.size
            internalMap = Array(height) { y -> Array(width) { x -> Dumbo(input[y][x], x, y) } }
/*            println("Initial map")
            printMap()*/
        }

        fun simulate(duration: Int): Int {
            for (step in 1..duration) {
                nextStep()
                flashDumbos(step)
/*                println()
                println("Map in step: $step")
                printMap()*/
            }
            return flashes
        }

        fun simulateFirstGlobalFlash(): Int {
            for (step in 1..Int.MAX_VALUE) {
                nextStep()
                flashDumbos(step)
                if (wasGlobalFlash()) {
                    return step
                }
            }
            return -1
        }

        private fun nextStep() {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val newEnergyLvl = internalMap[y][x].energyLvl + 1
                    internalMap[y][x] = internalMap[y][x].copy(energyLvl = newEnergyLvl)
                }
            }
        }

        private fun flashDumbos(step: Int) {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val dumbo = internalMap[y][x]
                    if (dumbo.energyLvl > 9 && dumbo.lastFlashStep < step) {
                        flash(x, y, step)
                        flashDumbos(step)
                    }
                }
            }
        }

        private fun flash(x: Int, y: Int, step: Int) {
            val center = internalMap[y][x]
            val left = internalMap.getOrNull(y)?.getOrNull(x - 1)
            val leftTop = internalMap.getOrNull(y - 1)?.getOrNull(x - 1)
            val top = internalMap.getOrNull(y - 1)?.getOrNull(x)
            val rightTop = internalMap.getOrNull(y - 1)?.getOrNull(x + 1)
            val right = internalMap.getOrNull(y)?.getOrNull(x + 1)
            val rightBottom = internalMap.getOrNull(y + 1)?.getOrNull(x + 1)
            val bottom = internalMap.getOrNull(y + 1)?.getOrNull(x)
            val leftBottom = internalMap.getOrNull(y + 1)?.getOrNull(x - 1)
            val dumbos = listOfNotNull(left, leftTop, top, rightTop, right, rightBottom, bottom, leftBottom)
            for (dumbo in dumbos) {
                if (dumbo.lastFlashStep < step) {
                    internalMap[dumbo.y][dumbo.x] = dumbo.copy(energyLvl = dumbo.energyLvl + 1)
                }
            }
            internalMap[center.y][center.x] = center.copy(energyLvl = 0, lastFlashStep = step)
            flashes++
        }

        private fun wasGlobalFlash(): Boolean {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (internalMap[y][x].energyLvl != 0) {
                        return false
                    }
                }
            }
            return true
        }

        private fun printMap() {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    print(internalMap[y][x].energyLvl.toString() + ",")
                }
                println()
            }
        }
    }

    fun part1(): Int {
        val input = File("./src/main/resources/day_11/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .map { line -> line.map { it.digitToInt() } }
        val map = DumboMap(input)
        return map.simulate(100)
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_11/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .map { line -> line.map { it.digitToInt() } }
        val map = DumboMap(input)
        return map.simulateFirstGlobalFlash()
    }
    println(part1())
    println(part2())
}