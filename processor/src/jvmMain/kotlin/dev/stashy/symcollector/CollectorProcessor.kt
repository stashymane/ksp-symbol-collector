package dev.stashy.symcollector

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import dev.stashy.symcollector.collectors.ListCollector
import dev.stashy.symcollector.collectors.MapCollector
import dev.stashy.symcollector.collectors.SymbolCollector

const val PACKAGE_NAME = "symbols"
const val FILE_NAME = "Symbols"

class CollectorProcessor(private val codegen: CodeGenerator, private val logger: KSPLogger) : SymbolProcessor {
    private var alreadyRun: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (alreadyRun) {
            logger.info("Collector already run; skipping")
            return emptyList()
        }

        val collectors = resolver.getCollectorAnnotations().toCollectors()

        val lines = collectors.map {
            it.collect(resolver)
            it.prepare(resolver)
            it.generate()
        }

        val files = collectors.flatMap { it.symbols.mapNotNull(KSAnnotated::containingFile) }.toSet()

        codegen.createNewFile(
            Dependencies(aggregating = false, sources = files.toTypedArray()),
            PACKAGE_NAME,
            FILE_NAME
        ).bufferedWriter().use { w ->
            w.appendLine("package $PACKAGE_NAME")
            w.appendLine()
            lines.forEach(w::appendLine)
        }

        alreadyRun = true
        return emptyList()
    }

    private fun Resolver.getCollectorAnnotations(): Sequence<KSClassDeclaration> =
        getSymbolsWithAnnotation(
            CollectSymbols::class.qualifiedName
                ?: throw IllegalStateException("CollectSymbols does not have a qualified name")
        ).filterIsInstance<KSClassDeclaration>()

    @OptIn(KspExperimental::class)
    private fun Sequence<KSClassDeclaration>.toCollectors(): Sequence<SymbolCollector> =
        map { it.getAnnotationsByType(CollectSymbols::class).first().toCollector(it) }

    private fun CollectSymbols.toCollector(annotation: KSClassDeclaration): SymbolCollector = when (type) {
        CollectSymbols.Type.List -> ListCollector(collectionName, collectionType, annotation, mutableListOf(), logger)
        CollectSymbols.Type.MapByProperty -> MapCollector(
            collectionName,
            collectionType,
            annotation,
            mutableListOf(),
            property,
            logger
        )
    }
//
//    private fun Sequence<Pair<KSAnnotated, CollectSymbols>>.collectSymbols(resolver: Resolver): Sequence<Pair<CollectSymbols, Sequence<KSAnnotated>>> =
//        map { (annotated, meta) ->
//            val qname = annotated.getQualifiedName()
//                ?: throw IllegalStateException("Annotation class does not have a qualified name")
//
//            meta to resolver.getSymbolsWithAnnotation(qname, true)
//        }
}
