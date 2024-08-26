package dev.stashy.symcollector.collectors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import dev.stashy.symcollector.getQualifiedName

interface SymbolCollector {
    val fieldName: String
    val fieldType: String
    val annotation: KSClassDeclaration
    val symbols: MutableList<KSAnnotated>

    val logger: KSPLogger

    fun collect(resolver: Resolver) {
        val name = annotation.getQualifiedName() ?: throw IllegalStateException("Annotation must have qualified name")
        resolver.getSymbolsWithAnnotation(name).toCollection(symbols)
        logger.info("$name: Collected ${symbols.size} symbols")
    }

    fun prepare(resolver: Resolver)
    fun generate(): String
}