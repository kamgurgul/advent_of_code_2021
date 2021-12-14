import java.io.File

data class Number(
    val value: Int,
    val isMarked: Boolean
)

data class Board(
    private val numbers: Array<Array<Number>>
) {

    fun markNumberIfExists(number: Int) {
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                if (numbers[i][j].value == number) {
                    numbers[i][j] = numbers[i][j].copy(isMarked = true)
                }
            }
        }
    }

    fun isInWinningConfiguration(): Boolean {
        val rotatedBoard = numbers.rotate()
        for (i in 0 until BOARD_SIZE) {
            val rowSum = numbers[i].fold(0) { acc, number ->
                if (number.isMarked) {
                    acc + 1
                } else {
                    acc
                }
            }
            if (rowSum == BOARD_SIZE) {
                return true
            }
            val columnSum = rotatedBoard[i].fold(0) { acc, number ->
                if (number.isMarked) {
                    acc + 1
                } else {
                    acc
                }
            }
            if (columnSum == BOARD_SIZE) {
                return true
            }
        }
        return false
    }

    fun getUnmarkedSum() = numbers.fold(0) { rowAcc, row ->
        rowAcc + row.fold(0) { numbersAcc, number ->
            if (number.isMarked) {
                numbersAcc
            } else {
                numbersAcc + number.value
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Array<*> && other.isArrayOf<Array<Number>>() && numbers.contentDeepEquals(other)
    }

    override fun hashCode(): Int {
        return numbers.contentDeepHashCode()
    }

    private fun Array<Array<Number>>.rotate(): Array<Array<Number>> {
        val rows = size
        val columns = first().size
        val rotated = Array(columns) { Array(rows) { Number(0, false) } }
        for (i in 0 until columns) {
            for (j in rows - 1 downTo 0) {
                val newX = rows - 1 - j
                rotated[i][newX] = get(j)[i]
            }
        }
        return rotated
    }

    companion object {
        const val BOARD_SIZE = 5

        fun parseBoard(input: List<String>): Board {
            val numbersArray = Array(BOARD_SIZE) { index ->
                input[index]
                    .trim()
                    .split("\\s+".toRegex())
                    .map { Number(it.toInt(), false) }
                    .toTypedArray()
            }
            return Board(numbersArray)
        }
    }
}

class Bingo(
    private val inputNumbers: List<Int>,
    private val boards: List<Board>
) {

    fun play(): Result {
        val wonBoards = mutableListOf<Board>()
        var usedNumber = 0
        for (number in inputNumbers) {
            usedNumber = number
            for (board in boards) {
                board.markNumberIfExists(number)
                if (board.isInWinningConfiguration()) {
                    wonBoards.add(board)
                }
            }
            if (wonBoards.isNotEmpty()) {
                break
            }
        }
        return Result(wonBoards, usedNumber)
    }

    fun findLastWinner(): Result {
        val possibleWinners = boards.toMutableList()
        var usedNumber = 0
        numbersLoop@ for (number in inputNumbers) {
            usedNumber = number
            for (board in possibleWinners) {
                board.markNumberIfExists(number)
            }
            val iterator = possibleWinners.iterator()
            while (iterator.hasNext()) {
                val board = iterator.next()
                if (board.isInWinningConfiguration()) {
                    if (possibleWinners.size == 1) {
                        break@numbersLoop
                    } else {
                        iterator.remove()
                    }
                }
            }
        }
        return Result(possibleWinners, usedNumber)
    }

    data class Result(
        val wonBoards: List<Board>,
        val lastNumber: Int
    )
}

fun main() {
    fun part1(): Int {
        val input = File("./src/main/resources/day_4/test_input.txt")
            .readLines()
            .filterNot { it.isEmpty() }
        val numbers = input.subList(0, 1)
            .first()
            .split(",")
            .map { it.toInt() }
        val boards = input.subList(1, input.size)
            .windowed(Board.BOARD_SIZE, Board.BOARD_SIZE)
            .map { Board.parseBoard(it) }
        val result = Bingo(numbers, boards).play()
        val firstWonBoard = result.wonBoards.first()
        val unmarkedSum = firstWonBoard.getUnmarkedSum()
        return unmarkedSum * result.lastNumber
    }

    fun part2(): Int {
        val input = File("./src/main/resources/day_4/input.txt")
            .readLines()
            .filterNot { it.isEmpty() }
        val numbers = input.subList(0, 1)
            .first()
            .split(",")
            .map { it.toInt() }
        val boards = input.subList(1, input.size)
            .windowed(Board.BOARD_SIZE, Board.BOARD_SIZE)
            .map { Board.parseBoard(it) }
        val result = Bingo(numbers, boards).findLastWinner()
        val lastWinnerBoard = result.wonBoards.first()
        val unmarkedSum = lastWinnerBoard.getUnmarkedSum()
        return unmarkedSum * result.lastNumber
    }

    println(part1())
    println(part2())
}