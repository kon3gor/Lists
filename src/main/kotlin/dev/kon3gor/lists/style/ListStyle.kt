package dev.kon3gor.lists.style

import dev.kon3gor.lists.ColorPallet
import kotlinx.css.*

val Style.listPage get() = className("list-page")

fun CssBuilder.listPage() = rule(Style.listPage) {
    display = Display.flex
    flexDirection = FlexDirection.column
    justifyContent = JustifyContent.start
    alignItems = Align.center
    backgroundColor = ColorPallet.backgroundColor
    color = ColorPallet.mainTextColor
    height = 100.vh
}

val Style.listContainer get() = className("list-container")

private val rightMargin = 10.vw
private val leftMargin = 5.vw
fun CssBuilder.listContainer() = rule(Style.listContainer) {
    display = Display.flex
    flexDirection = FlexDirection.column
    justifyContent = JustifyContent.start
    alignItems = Align.center
    width = 100.vw - rightMargin - leftMargin
    marginRight = rightMargin
    marginLeft = leftMargin
    marginTop = 2.vw
}
