package dev.kon3gor.lists.style

import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.valueParameters

object RuleCollector {
    private val registry = mutableMapOf<String, FutureRule>()

    fun add(rule: FutureRule) {
        check(rule.ruleName !in registry.keys)
        registry[rule.ruleName] = rule
    }

    fun materialize(builder: CssBuilder) {
        for (rule in registry.values) {
            rule(builder)
        }
    }
}

fun CssBuilder.extend(other: FutureRule) {
    val ruleSet = other.ruleSet
    ruleSet(this)
}

fun CssBuilder.materialize(rule: FutureRule) {
    rule(this)
}

sealed interface MyRule {
    val regularName: String
    val ruleName : String
}

data class ClassRule(override val regularName: String) : MyRule {
    override val ruleName = ".$regularName"
}

data class IdRule(override val regularName: String): MyRule {
    override val ruleName = "#$regularName"
}

class FutureRule internal constructor(
    internal val rule: MyRule,
    internal val ruleSet: RuleSet,
) {

    val ruleName get() = rule.ruleName
    val regularName get() = rule.regularName

    operator fun invoke(builder: CssBuilder) = with(builder) {
        rule(ruleName, ruleSet)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is FutureRule) {
            return false
        }
        return other.rule == rule
    }

    override fun hashCode(): Int {
        return rule.hashCode()
    }

    override fun toString(): String {
        return ruleName
    }
}

fun autoClass(ruleSet: RuleSet): AutoRule {
    return autoRule(::ClassRule, ruleSet)
}

fun autoId(ruleSet: RuleSet): AutoRule {
    return autoRule(::IdRule, ruleSet)
}

fun autoRule(ruleCreator: RuleCreator, ruleSet: RuleSet): AutoRule {
    return AutoRule(ruleCreator, ruleSet)
}

typealias RuleCreator = (String) -> MyRule

class AutoRule(
    private val ruleCreator: RuleCreator,
    private val ruleSet: RuleSet,
) : ReadOnlyProperty<Any?, FutureRule> {
    private var instance: FutureRule? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): FutureRule {
        if (instance == null) {
            val tmp = FutureRule(ruleCreator(property.name), ruleSet)
            instance = tmp
        }
        return instance!!
    }
}