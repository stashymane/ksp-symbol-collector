package dev.stashy.symcollector

/**
 * Marks an annotation as a collector for symbols.
 *
 * @property collectionName The name of the property your symbols will be placed in.
 * @property collectionType The fully-qualified type of your collection.
 * @property packageName The package your collected symbols will be placed into.
 * @property type The type of collection to be done.
 * @property property The property to collect with - only applies to `Type.MapByProperty`.
 *
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class CollectSymbols(
    val collectionName: String = "symbols",
    val collectionType: String = "kotlin.reflect.KClass<*>",
    val packageName: String = "symbols",
    val type: Type = Type.List,
    val property: String = ""
) {
    enum class Type {
        /**
         * Collects all symbols to one list.
         */
        List,

        /**
         * Collects all symbols to a map, indexed by a property.
         */
        MapByProperty
    }
}
