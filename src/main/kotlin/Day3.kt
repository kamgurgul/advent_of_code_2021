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

fun main() {
    val input = File("./resources/day_3/input.txt")
        .readLines()
        .filterNot { it.isEmpty() }
        .map { it.toCharArray() }
        .toTypedArray()

    val recordsCount = input.size
    val recordSize = input[0].size

    val rotatedInput = Array(recordSize) { CharArray(recordsCount) { '0' } }
    for (i in 0 until recordSize) {
        for (j in recordsCount - 1 downTo 0) {
            val newX = recordsCount - 1 - j
            rotatedInput[i][newX] = input[j][i]
        }
    }

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

    println(binaryGamma)
    println(binaryEpsilon)

    val gamma = Integer.parseInt(binaryGamma, 2)
    val epsilon = Integer.parseInt(binaryEpsilon, 2)

    println(gamma)
    println(epsilon)

    val result = gamma * epsilon
    println(result)
}