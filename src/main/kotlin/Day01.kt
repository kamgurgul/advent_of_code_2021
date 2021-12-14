import java.io.File

fun main() {
    val sonarValues = File("./src/main/resources/day_1/input.txt")
        .readLines()
        .filterNot { it.isEmpty() }
        .map { it.toInt() }
        .windowed(3)
        .map { it.sum() }

    var counter = 0
    for (i in 1 until sonarValues.size) {
        if (sonarValues[i - 1] < sonarValues[i]) {
            counter++
        }
    }
    println(counter)
}