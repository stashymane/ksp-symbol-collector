package yourproject

import symbols.first
import symbols.second

fun printSymbols() {
    first.forEach { println("${it.qualifiedName}") }
    second.forEach { (name, value) -> println("$name => ${value.qualifiedName}") }
}
