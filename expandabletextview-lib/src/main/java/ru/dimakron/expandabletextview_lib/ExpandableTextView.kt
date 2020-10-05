package ru.dimakron.expandabletextview_lib

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
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

    var trimCollapsedText: CharSequence = ""
    var trimExpandedText: CharSequence = ""
    var colorClickableText = Color.BLUE

    var onInterceptTrimText: ((s: Spannable, trimText: CharSequence) -> Unit)? = null
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
            trimLength = a?.getInt(R.styleable.ExpandableTextView_trimLength, trimLength)?: trimLength
            trimCollapsedText = a?.getString(R.styleable.ExpandableTextView_trimCollapsedText)?: resources.getString(R.string.read_more_show)
            trimExpandedText = a?.getString(R.styleable.ExpandableTextView_trimExpandedText)?: resources.getString(R.string.read_more_hide)
            colorClickableText = a?.getColor(R.styleable.ExpandableTextView_colorClickableText, colorClickableText)?: colorClickableText
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
            val collapsedSpannable = SpannableString("${rawText.subSequence(0, trimLength)}... $trimCollapsedText")
            collapsedSpannable.setSpan(clickableSpan, collapsedSpannable.length - (trimCollapsedText.length), collapsedSpannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            onInterceptTrimText?.invoke(collapsedSpannable, trimCollapsedText)
            collapsedText = collapsedSpannable

            val expandedSpannable = SpannableString("$rawText $trimExpandedText")
            expandedSpannable.setSpan(clickableSpan, expandedSpannable.length - (trimExpandedText.length), expandedSpannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            onInterceptTrimText?.invoke(expandedSpannable, trimExpandedText)
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
            ds.color = colorClickableText
        }
    }
}