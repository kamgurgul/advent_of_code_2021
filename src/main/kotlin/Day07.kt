import java.io.File
import kotlin.math.abs

fun main() {
    fun minFuel(input: List<Int>, stableRate: Boolean = true): Int {
        val min = input.minOf { it }
        val max = input.maxOf { it }
        return (min..max).minOf { alignPosition ->
            input.sumOf { position ->
                val n = abs(alignPosition - position)
                if (stableRate) n else n * (n + 1) / 2
            }
        }
    }

    fun part1(): Int {
        val input = File("./src/main/resources/day_7/input.txt")
            .readLines()
            .first()
            .split(",")
            .map { it.toInt() }

        return minFuel(input)
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_7/input.txt")
            .readLines()
            .first()
            .split(",")
            .map { it.toInt() }

        return minFuel(input, stableRate = false)
    }
    println(part1())
    println(part2())
}