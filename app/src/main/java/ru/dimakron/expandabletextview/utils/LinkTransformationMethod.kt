package ru.dimakron.expandabletextview.utils

import android.graphics.Rect
import android.text.Spannable
import android.text.Spanned
import android.text.method.TransformationMethod
import android.text.style.URLSpan
import android.text.util.Linkify
import android.view.View
import android.widget.TextView

class LinkTransformationMethod(private val onLinkClick: (String) -> Unit) : TransformationMethod {

    override fun getTransformation(source: CharSequence?, view: View?): CharSequence? {
        if (view !is TextView) return source

        Linkify.addLinks(view, Linkify.WEB_URLS)

        val text = view.text as? Spannable ?: return source

        val spans = text.getSpans(0, view.length(), URLSpan::class.java)

        for (oldSpan in spans) {
            val start = text.getSpanStart(oldSpan)
            val end = text.getSpanEnd(oldSpan)
            val url = oldSpan.url
            text.removeSpan(oldSpan)
            text.setSpan(
                CustomURLSpan(url, onLinkClick),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return text
    }

    override fun onFocusChanged(p0: View?, p1: CharSequence?, p2: Boolean, p3: Int, p4: Rect?) {
    }
}