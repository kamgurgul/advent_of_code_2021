import java.io.File

fun main() {
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

    fun loadDataAsArray(): Array<CharArray> {
        return File("./src/main/resources/day_3/input.txt")
            .readLines()
            .filterNot { it.isEmpty() }
            .map { it.toCharArray() }
            .toTypedArray()
    }

    fun part1(): Int {
        val input = loadDataAsArray()
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

    fun findOxygenRowRec(input: Array<CharArray>, rotatedInput: Array<CharArray>, iteration: Int = 0): String {
        if (input.size == 1) {
            return input.first().joinToString("")
        }
        val mutableInput = input.toMutableList()
        val row = rotatedInput[iteration]
        val ones = row.count { char -> char == '1' }
        val zeros = row.size - ones
        if (ones >= zeros) {
            mutableInput.removeAll { it[iteration] == '0' }
        } else {
            mutableInput.removeAll { it[iteration] == '1' }
        }
        val array = mutableInput.toTypedArray()
        return findOxygenRowRec(array, array.rotate(), iteration + 1)
    }

    fun findCO2RowRec(input: Array<CharArray>, rotatedInput: Array<CharArray>, iteration: Int = 0): String {
        if (input.size == 1) {
            return input.first().joinToString("")
        }
        val mutableInput = input.toMutableList()
        val row = rotatedInput[iteration]
        val ones = row.count { char -> char == '1' }
        val zeros = row.size - ones
        if (zeros <= ones) {
            mutableInput.removeAll { it[iteration] == '1' }
        } else {
            mutableInput.removeAll { it[iteration] == '0' }
        }
        val array = mutableInput.toTypedArray()
        return findCO2RowRec(array, array.rotate(), iteration + 1)
    }

    fun part2(): Int {
        val input = loadDataAsArray()
        val rotatedInput = input.rotate()

        val binaryOxygen = findOxygenRowRec(input, rotatedInput)
        val binaryCO2 = findCO2RowRec(input, rotatedInput)

        val oxygen = binaryOxygen.toInt(2)
        val co2 = binaryCO2.toInt(2)

        return oxygen * co2
    }

    println(part1())
    println(part2())
}