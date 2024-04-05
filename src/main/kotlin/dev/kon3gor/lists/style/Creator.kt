package dev.kon3gor.lists.style

import dev.kon3gor.lists.AnyComponent
import dev.kon3gor.lists.ColorPallet
import dev.kon3gor.lists.Component
import dev.kon3gor.lists.page.list.components.*
import kotlinx.css.*

object Style

sealed interface RuleSelector {
    class Clazz(override val raw: String) : RuleSelector {
        override val asRule get() = ".$raw"
    }

    val asRule: String
    val raw: String
}


internal fun className(name: String) = RuleSelector.Clazz(name)

fun CssBuilder.create() {
    html {
        backgroundColor = ColorPallet.backgroundColor
    }

    itemStyle()
    completedItemStyle()
    listPage()
    listContainer()
    userImage()
    itemButtonsContainer()
    listBreaker()
    editButton()
    itemTitle()
    itemTitleEnabled()

    fromComponent(AddNewItemComponent)
    fromComponent(ListHeaderComponent)

    materialize(itemButtonDoneStyle)
    materialize(itemButtonDeleteStyle)
    materialize(itemButtonIconStyle)

    RuleCollector.materialize(this)
}

internal fun RuleContainer.rule(selector: RuleSelector, block: RuleSet): Rule {
    return rule(selector.asRule, block)
}

fun classSet(vararg rules: RuleSelector.Clazz): Set<String> {
    return rules.asSequence().map { it.raw }.toSet()
}

fun classSet(vararg rules: FutureRule): Set<String> {
    return rules.asSequence()
        .filter { it.rule is ClassRule }
        .map { it.regularName }
        .toSet()
}

fun CssBuilder.fromComponent(component: AnyComponent) {
    for (rule in component.knownStyles) {
        rule(this)
    }
}