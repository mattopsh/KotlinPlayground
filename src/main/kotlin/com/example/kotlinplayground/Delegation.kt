interface Base {
    val name: String
    val name2: String
    fun print()
    fun print2()
}

class BaseImpl(private val value: Int) : Base {

    override val name: String = "[BaseImpl - name]"
    override val name2: String = "[BaseImpl - name2]"

    override fun print() {
        println("BaseImpl - $name-$value")
    }

    override fun print2() {
        println("BaseImpl - $name-$value")
    }
}

class D(b: Base) : Base by b {

    override val name: String = "[D - name]"

    override fun print2() {
        println("D - $name - $name2")
    }
}

class D2(b: Base) : Base by b

fun main() {
    val impl = BaseImpl(10)
    val d = D(impl)
    val d2 = D2(impl)
    d.print()
    d.print2()
    d2.print()
    d2.print2()
}