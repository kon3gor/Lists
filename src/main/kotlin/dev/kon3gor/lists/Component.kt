package dev.kon3gor.lists

import dev.kon3gor.lists.style.ClassRule
import dev.kon3gor.lists.style.FutureRule
import kotlinx.html.FlowContent

typealias AnyComponent = Component<*>


abstract class Component<Properties> {
    open val knownStyles: Set<FutureRule> = emptySet()

    abstract fun FlowContent.render(properties: Properties)

    internal fun render(context: FlowContent, properties: Properties) = with(context) { render(properties) }

    fun classSet(vararg rules: FutureRule): Set<String> {
        return rules.asSequence()
            .map { require(it in knownStyles) { "Style $it is not defined for the component ${javaClass.simpleName}" }; it }
            .filter { it.rule is ClassRule }
            .map { it.regularName }
            .toSet()
    }

}
