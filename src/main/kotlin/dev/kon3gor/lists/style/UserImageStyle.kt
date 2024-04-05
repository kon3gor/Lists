package dev.kon3gor.lists.style

import kotlinx.css.*

val Style.userImage get() = className("user-image")

private val size = 8.vw
fun CssBuilder.userImage() = rule(Style.userImage) {
        width = size
        height = size
        borderRadius = size
        position = Position.absolute
        top = 0.px
        left = 0.px
}
