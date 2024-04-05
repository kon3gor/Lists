package dev.kon3gor.lists.style

import dev.kon3gor.lists.ColorPallet
import kotlinx.css.*

val Style.listBreaker get() = className("list-breaker")

fun CssBuilder.listBreaker() = rule(Style.listBreaker) {
    color = ColorPallet.breakerColor
    width = 70.vw
    borderStyle = BorderStyle.solid
    borderRadius = 20.px
    marginTop = 2.vw
}