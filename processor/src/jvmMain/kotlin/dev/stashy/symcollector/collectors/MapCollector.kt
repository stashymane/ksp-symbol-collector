package dev.stashy.symcollector.collectors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import dev.stashy.symcollector.getQualifiedName

class MapCollector(
    override val fieldName: String,
    override val fieldType: String,
    override val annotation: KSClassDeclaration,
    override val symbols: MutableList<KSAnnotated>,
    private val propertyName: String,
    override val logger: KSPLogger
) : SymbolCollector {
    private val entries = mutableMapOf<String, String>()

    override fun prepare(resolver: Resolver) {
        symbols.associate { symbol ->
            val propertyValue =
                symbol.annotations.filter { it.shortName.asString() == annotation.simpleName.asString() && it.getQualifiedName() == annotation.getQualifiedName() }
                    .first().arguments.find { it.name?.asString() == propertyName }!!.value!!.toString()
            val item = (symbol as KSDeclaration).getQualifiedName(true)!!
            propertyValue to item
        }.toMap(entries)
    }

    override fun generate(): String {
        val lines = entries.entries.joinToString(",\n") { (prop, symbol) -> "    \"$prop\" to $symbol" }
        return "val $fieldName: Map<String, $fieldType> = mapOf(\n$lines\n)"
    }
}