import java.io.File

fun main() {

    fun part1(): Int {
        return File("./src/main/resources/day_8/input.txt")
            .readLines()
            .map {
                it.split("|")
                    .last()
                    .trim()
                    .split(" ")
            }
            .flatten()
            .count { it.length in listOf(2, 3, 4, 7) }
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_8/input.txt")
            .readLines()

        return 0
    }
    println(part1())
    println(part2())
}