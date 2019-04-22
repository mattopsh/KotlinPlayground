fun list() {
    listOf(1, 3, 4, 55, 34)
        .map { println("map: $it"); it * 2 }
        .filter { println("filter: $it"); it % 2 == 0 }
}

fun sequence() {
    listOf(1, 3, 4, 55, 34)
        .asSequence()
        .map { println("map: $it"); it * 2 }
        .filter { println("filter: $it"); it % 2 == 0 }
        .toList()
}

fun fib() {
    val fib = generateSequence(Pair(0, 1), { (first, second) -> Pair(second, first + second) }).map { it.first }
    val f = fib.take(10)
    println(f.toList())
}

fun fib(n: Int) {
    tailrec fun f(x1: Int, x2: Int, xs: List<Int>): List<Int> {
        return if (xs.size < n) {
            f(x2, x1 + x2, xs + x1)
        } else {
            xs
        }
    }
    println(f(0, 1, listOf()))
}

fun main() {
    list()
    sequence()
    fib()
    fib(10)
}