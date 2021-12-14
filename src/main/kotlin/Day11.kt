import java.io.File

fun main() {

    fun part1(): Int {
        val input = File("./src/main/resources/day_11/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }

        return 0
    }

    fun part2(): Long {
        val points = File("./src/main/resources/day_11/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }

        return 0
    }
    println(part1())
    println(part2())
}