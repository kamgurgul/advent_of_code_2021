import java.io.File

fun flipBinaryString(input: String): String {
    val builder = StringBuilder()
    for (char in input) {
        if (char == '0') {
            builder.append('1')
        } else {
            builder.append('0')
        }
    }
    return builder.toString()
}

fun Array<CharArray>.rotate(): Array<CharArray> {
    val rows = size
    val columns = first().size
    val rotated = Array(columns) { CharArray(rows) { '0' } }
    for (i in 0 until columns) {
        for (j in rows - 1 downTo 0) {
            val newX = rows - 1 - j
            rotated[i][newX] = get(j)[i]
        }
    }
    return rotated
}

fun loadDataAsMatrix(): Array<CharArray> {
    return File("./src/main/resources/day_3/test_input.txt")
        .readLines()
        .filterNot { it.isEmpty() }
        .map { it.toCharArray() }
        .toTypedArray()
}

fun part1(): Int {
    val input = loadDataAsMatrix()
    val rotatedInput = input.rotate()

    val binaryGamma = rotatedInput
        .map {
            val ones = it.count { char -> char == '1' }
            val zeroes = it.size - ones
            if (ones > zeroes) {
                '1'
            } else {
                '0'
            }
        }
        .joinToString("")
    val binaryEpsilon = flipBinaryString(binaryGamma)

    val gamma = binaryGamma.toInt(2)
    val epsilon = binaryEpsilon.toInt(2)

    return gamma * epsilon
}

fun findOxygenRow(input: Array<CharArray>, rotatedInput: Array<CharArray>): String {
    val mutableInput = input.toMutableList()
    val mutableRotatedInput = rotatedInput.toMutableList()
    for ((i, row) in mutableRotatedInput.withIndex()) {
        val ones = row.count { char -> char == '1' }
        val zeros = row.size - ones
        if (ones >= zeros) {
            mutableInput.removeAll { it[i] == '0' }
        } else {
            mutableInput.removeAll { it[i] == '1' }
        }

        /*mutableRotatedInput.clear()
        mutableInput.addAll(mutableInput.toTypedArray().rotate())*/

        if (mutableInput.size == 1) {
            break
        }
    }
    return mutableInput.first().joinToString("")
}

fun part2(): Int {
    val input = loadDataAsMatrix()
    val rotatedInput = input.rotate()

    val binaryOxygen = findOxygenRow(input, rotatedInput)

    println(binaryOxygen)

    return 0
}

fun main() {
    println(part1())
    println(part2())
}