import java.lang.Exception
import kotlin.sequences.*

sealed class Either<E, A>

data class Left<E, A>(val value: E) : Either<E, A>()

data class Right<E, A>(val value: A) : Either<E, A>()

fun <E, A> left(e: E): Either<E, A> = Left(e)

fun <E, A> right(a: A): Either<E, A> = Right(a)

fun <E, A> pure(a: A): Either<E, A> = right(a)

infix fun <E, A, B> Either<E, A>.`++`(f: (A) -> (B)): Either<E, B> =
    when (this) {
        is Left -> left(this.value)
        is Right -> right(f(this.value))
    }


infix fun <E, A, B> Either<E, A>.`+++`(f: (A) -> Either<E, B>): Either<E, B> =
    when (this) {
        is Left -> left(this.value)
        is Right -> f(this.value)
    }

fun <E, A> `for`(fn: suspend SequenceScope<Either<E, A>>.() -> Unit): Either<E, A> {
    return sequence<Either<E, A>> { fn() }.first()
}

suspend fun <E, A> SequenceScope<Either<E, A>>.yield(value: A) = yield(right(value))

suspend fun <E, A, B> SequenceScope<Either<E, A>>.`~`(e: Either<E, B>): B =
    when (e) {
        is Left -> {
            yield(left(e.value));
            throw Exception()
        }
        is Right -> e.value
    }

fun forComprehensionExample(): Either<String, Int> =
    `for` {
        val a = `~`(right(12))
        val b = `~`(right("aaa"))
        val c = `~`(right(53))
        yield(a + c)
    }

fun forComprehensionExample2(): Either<String, Int> =
    `for` {
        val a = `~`(right(12))
        val b = `~`(left<String, Int>("err"))
        val c = `~`(right(53))
        yield(a + c)
    }

fun mapExample(): Either<String, String> =
    right<String, Int>(1) `++` {
        "$it"
    } `++` {
        "mapEx:$it"
    }

fun flatMapExample(): Either<String, Int> =
    right<String, Int>(1) `+++` {
        right<String, Int>(12)
    } `+++` {
        right<String, String>("ggg")
    } `+++` {
        left<String, Int>("aaa")
    } `+++` {
        left<String, Int>("bbb")
    } `+++` {
        left<String, Int>("ccc")
    }

fun main() {
    val res1 = forComprehensionExample()
    val res2 = forComprehensionExample2()
    val res3 = mapExample()
    val res4 = flatMapExample()
    print("")
}