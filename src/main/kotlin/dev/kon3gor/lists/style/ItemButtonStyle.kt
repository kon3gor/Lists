package dev.kon3gor.lists.style

import dev.kon3gor.lists.ColorPallet
import kotlinx.css.*
import kotlinx.css.properties.Timing.Companion.ease
import kotlinx.css.properties.ms

private val size = 4.vw
val itemButtonBase by autoClass {
    width = size
    height = size
    marginRight = 15.px
    borderRadius = size / 3
    display = Display.flex
    justifyContent = JustifyContent.center
    alignItems = Align.center
    cursor = Cursor.pointer
    boxSizing = BoxSizing.borderBox
}

val itemButtonDoneStyle by autoClass {
    extend(itemButtonBase)
    border = Border(0.5.vw, BorderStyle.solid, ColorPallet.markCompletedBtnColor)
    color = ColorPallet.markCompletedBtnColor
}

val itemButtonDeleteStyle by autoClass {
    extend(itemButtonBase)
    border = Border(0.5.vw, BorderStyle.solid, ColorPallet.deleteBtnColor)
    color = ColorPallet.deleteBtnColor
}

val itemButtonIconStyle by autoClass {
    opacity = 0
    transitionProperty = "opacity"
    transitionTimingFunction = ease
    transitionDuration = 100.ms

    hover {
        opacity = 1
    }
}