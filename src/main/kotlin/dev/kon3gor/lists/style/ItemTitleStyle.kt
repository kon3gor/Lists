package dev.kon3gor.lists.style

import dev.kon3gor.lists.ColorPallet
import kotlinx.css.*

val Style.itemTitle get() = className("item-title")

fun CssBuilder.itemTitle() = rule(Style.itemTitle) {
    backgroundColor = Color.transparent
    marginLeft = 5.vw
    // border = Border.none
    color = ColorPallet.mainTextColor
    outline = Outline.none
    pointerEvents = PointerEvents.none
    width = 100.pct - (4.vw + 15.px) * 2
    resize = Resize.none
    minHeight = 2.em
    overflowY = Overflow.auto
    overflowX = Overflow.hidden

    useCommonFont()
    fontSize = 2.em
    fontWeight = FontWeight.w400
}

val Style.itemTitleEnabled get() = className("item-title-enabled")

fun CssBuilder.itemTitleEnabled() = rule(Style.itemTitleEnabled) {
    pointerEvents = PointerEvents.auto
}
