package ru.dimakron.expandabletextview_lib

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class ExpandableTextView: AppCompatTextView {

    private var rawText: CharSequence = ""
    private var collapsedText: CharSequence = ""
    private var expandedText: CharSequence = ""

    private var bufferType = BufferType.NORMAL
    private var isExpanded = false

    var trimLength = 240
        set(value) {
            field = value
            update()
        }

    var collapsedLinkText: CharSequence = ""
        set(value) {
            field = value
            update()
        }

    var expandedLinkText: CharSequence = ""
        set(value) {
            field = value
            update()
        }

    var linkColor = Color.BLUE
        set(value) {
            field = value
            update()
        }

    var onCustomizeSpannable: ((s: Spannable, linkText: CharSequence) -> Unit)? = null
        set(value) {
            field = value
            update()
        }

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet? = null) {
        val a = attrs?.let { context.theme.obtainStyledAttributes(it, R.styleable.ExpandableTextView, 0, 0) }
        try {
            trimLength = a?.getInt(R.styleable.ExpandableTextView_etv_trimLength, trimLength)?: trimLength
            collapsedLinkText = a?.getString(R.styleable.ExpandableTextView_etv_textCollapsedLink)?: resources.getString(R.string.read_more_show)
            expandedLinkText = a?.getString(R.styleable.ExpandableTextView_etv_textExpandedLink)?: resources.getString(R.string.read_more_hide)
            linkColor = a?.getColor(R.styleable.ExpandableTextView_etv_textColorLink, linkColor)?: linkColor
        } finally {
            a?.recycle()
        }

        update()
    }

    private fun update() {
        if(trimLength >= rawText.length){
            collapsedText = rawText
            expandedText = rawText
        } else {
            val collapsedSpannable = SpannableStringBuilder()
                    .append(rawText.subSequence(0, trimLength))
                    .append("... ")
                    .append(collapsedLinkText)
            collapsedSpannable.setSpan(clickableSpan, collapsedSpannable.length - (collapsedLinkText.length), collapsedSpannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            onCustomizeSpannable?.invoke(collapsedSpannable, collapsedLinkText)
            collapsedText = collapsedSpannable

            val expandedSpannable = SpannableStringBuilder()
                    .append(rawText)
                    .append(" ")
                    .append(expandedLinkText)
            expandedSpannable.setSpan(clickableSpan, expandedSpannable.length - (expandedLinkText.length), expandedSpannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            onCustomizeSpannable?.invoke(expandedSpannable, expandedLinkText)
            expandedText = expandedSpannable
        }

        super.setText(if(isExpanded) expandedText else collapsedText, bufferType)

        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        rawText = text?: ""
        bufferType = type?: BufferType.NORMAL
        update()
    }

    fun toggle() {
        isExpanded = !isExpanded
        super.setText(if(isExpanded) expandedText else collapsedText, bufferType)
    }

    private val clickableSpan = object: ClickableSpan() {

        override fun onClick(widget: View) {
            toggle()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = linkColor
        }
    }
}