import java.io.File

data class Coords(
    val x: Int,
    val y: Int
)

data class VentLine(
    val from: Coords,
    val to: Coords
) {

    fun isVerticalOrHorizontalLine() =
        from.x == to.x || from.y == to.y
}

class VentsMap(
    val ventLines: List<VentLine>,
    val map: List<List<Int>>
) {

}

fun main() {
    fun part1(): String {
        val input = File("./src/main/resources/day_4/input.txt")
            .readLines()
            .filterNot { it.isEmpty() }

        return ""
    }
    println(part1())
}