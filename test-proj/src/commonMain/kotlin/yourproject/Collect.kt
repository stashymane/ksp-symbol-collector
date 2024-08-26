package yourproject

import dev.stashy.symcollector.CollectSymbols

@CollectSymbols("first")
annotation class CollectList()

@CollectSymbols("second", type = CollectSymbols.Type.MapByProperty, property = "name")
annotation class CollectMap(val name: String)
