# KSP Symbol Collector

Allows you to define an annotation which collects symbols into a list or map.

Originally made for helping with serialization without reflection.

## Current issues

* KSP2 does not work.
  [not](https://github.com/google/ksp/issues/2001) [my](https://github.com/google/ksp/issues/1823) [fault](https://github.com/google/ksp/issues/1941)
* Multiplatform projects work like the example **only** if there is more than one target.
  Needs fix from either KMP gradle or KSP.

## Setup

Check out the `test-proj` module for a Kotlin Multiplatform example on how to set everything up.
Setup for Kotlin JVM is just like any other KSP project.

## Examples

**Note** - these examples are abbreviated to look good, generated code looks a bit less appealing

<table>
<tr>
<th>Input</th>
<th>Output</th>
</tr>
<tr>
<td>

```kotlin
@CollectSymbols("allClasses")
annotation class CollectToList

@CollectToList
class Foo

@CollectToList
class Bar
```

</td>
<td>

`symbols/Symbols.kt`

```kotlin
val allClasses: Collection<KClass<*>> =
    listOf(Foo::class, Bar::class)
```

</td>
</tr>
</table>


<table>
<tr>
<th>Input</th>
<th>Output</th>
</tr>
<tr>
<td>

```kotlin
@CollectSymbols(
    "symbolMap",
    type = Type.MapByProperty,
    property = "name"
)
annotation class ToMap(val name: String)

@ToMap("foo-class")
class Foo

@ToMap("bar-class")
class Bar
```

</td>
<td>

`symbols/Symbols.kt`

```kotlin
val symbolMap: Map<String, KClass<*>> =
    mapOf(
        "foo-class" to Foo::class,
        "bar-class" to Bar::class
    )
```

</td>
</tr>
</table>
