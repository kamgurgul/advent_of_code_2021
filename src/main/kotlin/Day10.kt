import java.io.File
import java.util.*

fun main() {

    val openingChars = listOf('(', '[', '{', '<')
    val closingChars = listOf(')', ']', '}', '>')

    val corruptedPointsMapping = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val incompletePointsMapping = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun findCorruptedChar(line: String): Char? {
        val stack = Stack<Char>()
        for (char in line) {
            when (char) {
                in openingChars -> {
                    stack.push(char)
                }
                in closingChars -> {
                    val expectedOpeningChar = openingChars[closingChars.indexOf(char)]
                    if (stack.pop() != expectedOpeningChar) {
                        return char
                    }
                }
                else -> throw IllegalArgumentException("Unsupported char")
            }
        }
        return null
    }

    fun fixIncompleteLine(line: String): List<Char> {
        val stack = Stack<Char>()
        val missedClosingChars = mutableListOf<Char>()
        for (char in line) {
            when (char) {
                in openingChars -> {
                    stack.push(char)
                }
                in closingChars -> {
                    stack.pop()
                }
                else -> throw IllegalArgumentException("Unsupported char")
            }
        }
        while (stack.isNotEmpty()) {
            missedClosingChars.add(closingChars[openingChars.indexOf(stack.pop())])
        }
        return missedClosingChars
    }

    fun part1(): Int {
        return File("./src/main/resources/day_10/input.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .mapNotNull { findCorruptedChar(it) }
            .sumOf { corruptedPointsMapping.getValue(it) }
    }

    fun part2(): Long {
        val points = File("./src/main/resources/day_10/input.txt")
            .readLines()
            .filter { it.isNotEmpty() && findCorruptedChar(it) == null }
            .map { fixIncompleteLine(it) }
            .map { it.fold(0L) { acc, char -> acc * 5 + incompletePointsMapping.getValue(char) } }
            .sorted()
        val middle = points.size / 2 // always odd
        return points[middle]
    }
    println(part1())
    println(part2())
}