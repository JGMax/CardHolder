package gortea.jgmax.cardholder.custom.model

import gortea.jgmax.cardholder.custom.BookmarkView

class BookmarkViewModel(private val view: BookmarkView) {
    var opened = false
    set(value) {
        if (field == value) return
        field = value
        view.setOpened(opened = field, withAnimation = true)
    }
}