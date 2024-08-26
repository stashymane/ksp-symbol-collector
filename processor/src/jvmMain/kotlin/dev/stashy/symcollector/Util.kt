package dev.stashy.symcollector

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

fun KSDeclaration.getQualifiedName(includePostfix: Boolean = false): String? {
    return when (this) {
        is KSClassDeclaration -> qualifiedName?.asString()?.plus(if (includePostfix) "::class" else "")
        is KSPropertyDeclaration -> qualifiedName?.asString()
        else -> qualifiedName?.asString()
    }
}

fun KSAnnotation.getQualifiedName() = annotationType.resolve().declaration.qualifiedName?.asString()
