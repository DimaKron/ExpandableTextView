package ru.dimakron.expandabletextview.utils

import android.os.Parcel
import android.text.style.URLSpan
import android.view.View

class CustomURLSpan : URLSpan {

    private val onLink: (String) -> Unit

    constructor(url: String?, onLink: (String) -> Unit) : super(url) {
        this.onLink = onLink
    }

    constructor(src: Parcel, onLink: (String) -> Unit) : super(src) {
        this.onLink = onLink
    }

    override fun onClick(widget: View) {
        onLink(url)
    }

}