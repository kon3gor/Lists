package dev.kon3gor.lists.style

import dev.kon3gor.lists.ColorPallet
import kotlinx.css.*
import kotlinx.css.properties.Timing.Companion.ease
import kotlinx.css.properties.Timing.Companion.easeOut
import kotlinx.css.properties.Transitions
import kotlinx.css.properties.ms
import kotlinx.css.properties.s

val Style.editButton get() = className("edit-button")

private val size = 3.vw
fun CssBuilder.editButton() = rule(Style.editButton) {
    width = size
    height = size
    borderRadius = size / 3
    display = Display.flex
    justifyContent = JustifyContent.center
    alignItems = Align.center
    backgroundColor = ColorPallet.breakerColor
    color = ColorPallet.backgroundColor
    position = Position.absolute
    opacity = 0
    transitionProperty = "opacity"
    transitionTimingFunction = ease
    transitionDuration = 100.ms
    cursor = Cursor.pointer

    hover {
        opacity = 1
    }
}
