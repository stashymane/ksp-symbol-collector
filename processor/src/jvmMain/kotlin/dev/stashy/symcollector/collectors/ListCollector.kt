package dev.stashy.symcollector.collectors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import dev.stashy.symcollector.getQualifiedName

class ListCollector(
    override val fieldName: String,
    override val fieldType: String,
    override val annotation: KSClassDeclaration,
    override val symbols: MutableList<KSAnnotated>,
    override val logger: KSPLogger
) : SymbolCollector {
    private val names = mutableListOf<String>()

    override fun prepare(resolver: Resolver) {
        symbols.map {
            val qn = (it as KSDeclaration).getQualifiedName()
                ?: throw IllegalStateException("Annotation class does not have a qualified name")
            "$qn::class"
        }.toCollection(names)
    }

    override fun generate(): String {
        return "val ${fieldName}: Collection<${fieldType}> = listOf(${names.joinToString()})"
    }
}