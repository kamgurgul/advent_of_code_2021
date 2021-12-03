import java.io.File

data class Coordinates(
    val x: Int,
    val y: Int,
    val aim: Int
)

data class Command(
    val direction: Direction,
    val unit: Int
) {

    companion object {
        fun parseCommand(input: String): Command {
            val (direction, length) = input.split(" ")
            return Command(Direction.fromString(direction), length.toInt())
        }
    }
}

enum class Direction {
    FORWARD, DOWN, UP;

    companion object {
        fun fromString(input: String): Direction {
            return when (input) {
                "forward" -> FORWARD
                "down" -> DOWN
                "up" -> UP
                else -> throw IllegalArgumentException("Unknown direction")
            }
        }
    }
}

object Navigator {

    fun navigate(current: Coordinates, command: Command): Coordinates {
        return when (command.direction) {
            Direction.FORWARD -> current.copy(
                x = current.x + command.unit,
                y = current.y + current.aim * command.unit
            )
            Direction.DOWN -> current.copy(aim = current.aim + command.unit)
            Direction.UP -> current.copy(aim = current.aim - command.unit)
        }
    }
}

fun main() {
    val coordinates = File("./resources/day_2/input.txt")
        .readLines()
        .filterNot { it.isEmpty() }
        .fold(Coordinates(0, 0, 0)) { coordinates, input ->
            Navigator.navigate(coordinates, Command.parseCommand(input))
        }

    println(coordinates.x * coordinates.y)
}