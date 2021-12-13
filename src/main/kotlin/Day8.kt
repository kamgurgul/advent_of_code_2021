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

        val validSegments = mapOf(
            setOf(0, 1, 2, 4, 5, 6) to 0,
            setOf(2, 5) to 1,
            setOf(0, 2, 3, 4, 6) to 2,
            setOf(0, 2, 3, 5, 6) to 3,
            setOf(1, 2, 3, 5) to 4,
            setOf(0, 1, 3, 5, 6) to 5,
            setOf(0, 1, 3, 4, 5, 6) to 6,
            setOf(0, 2, 5) to 7,
            setOf(0, 1, 2, 3, 4, 5, 6) to 8,
            setOf(0, 1, 2, 3, 5, 6) to 9
        )

        fun <T> allPermutations(set: Set<T>): Set<List<T>> {
            if (set.isEmpty()) return emptySet()

            fun <T> internalPermutations(list: List<T>): Set<List<T>> {
                if (list.isEmpty()) return setOf(emptyList())

                val result: MutableSet<List<T>> = mutableSetOf()
                for (i in list.indices) {
                    internalPermutations(list - list[i]).forEach { item ->
                        result.add(item + list[i])
                    }
                }
                return result
            }

            return internalPermutations(set.toList())
        }

        fun randomConfig(words: List<String>, expectedNumbers: List<String>): Int {
            val inputCables = 0..6
            val inputChars = 'a'..'g'
            val inputCablesPermutations = allPermutations(inputCables.toSet())

            fun getMapping(): Map<Char, Int> {
                permute@ for (perm in inputCablesPermutations) {
                    val zipped = inputChars.zip(perm).toMap()
                    for (word in words) {
                        val mapped = word.map { zipped.getValue(it) }.toSet()
                        val isValidDigit = validSegments.containsKey(mapped)
                        if (!isValidDigit) continue@permute
                    }
                    return zipped
                }
                throw IllegalStateException("Cannot find correct mapping")
            }

            val mapping = getMapping()
            val num = expectedNumbers.joinToString("") { digit ->
                val segments = digit.map { mapping.getValue(it) }.toSet()
                val dig = validSegments.getValue(segments)
                "$dig"
            }
            return num.toInt()
        }

        val lists = input.map {
            val (inputSegments, output) = it.split(" | "); inputSegments.split(" ") to output.split(" ")
        }
        return lists.sumOf { (input, output) ->
            randomConfig(input, output)
        }
    }
    println(part1())
    println(part2())
}
