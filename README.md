# KSP Symbol Collector

Allows you to define an annotation which collects symbols into a list or map.

Originally made for helping with serialization without reflection.

## List example

### Input

```kotlin
@CollectSymbols("allClasses")
annotation class CollectToList

@CollectToList
class Foo

@CollectToList
class Bar
```

### Output

`symbols/Symbols.kt`

```kotlin
val allClasses: Collection<kotlin.reflect.KClass<*>> = listOf(Foo::class, Bar::class)
```

## Current issues

* KSP2 does not work.
  [not](https://github.com/google/ksp/issues/2001) [my](https://github.com/google/ksp/issues/1823) [fault](https://github.com/google/ksp/issues/1941)
* Multiplatform projects work like the example **only** if there is more than one target.
  Needs fix from either KMP gradle or KSP.

## Setup

Check out the `test-proj` module for a Kotlin Multiplatform example on how to set everything up.
Setup for Kotlin JVM is just like any other KSP project.
