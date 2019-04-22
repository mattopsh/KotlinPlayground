import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest


fun getItemRequest(init: GetItemRequest.() -> Unit) = GetItemRequest().apply {
    init()
}

fun GetItemRequest.tableName(init: () -> String) {
    this.tableName = init()
}

// version 1
fun GetItemRequest.key(init: () -> Map<String, AttributeValue>) {
    this.key = init()
}

// version 2
fun GetItemRequest.key(vararg pairs: Pair<String, AttributeValue>) {
    this.key = pairs.toMap()
}

fun String.s() = AttributeValue().also {
    it.s = this
}

fun List<String>.ss() = AttributeValue().apply {
    this.setSS(this@ss)
}


fun main() {
    val r = getItemRequest {
        tableName { "TableName" }
//        key {                                     // version 1
//            mapOf(
//                "id"    to "123-123-123".s(),
//                "other" to listOf("a", "b").ss()
//            )
//        }
        key(                                 // version 2
            "id"    to "123-123-123".s(),
            "other" to listOf("a", "b").ss()
        )
    }
    print("")
}