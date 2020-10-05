package ru.dimakron.expandabletextview_lib


import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class ExpandableTextView: AppCompatTextView {

    companion object{
        const val ELLIPSIZE = "... "
    }

    private var customText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var readMore = true

    var trimLength = 240
        set(value) {
            field = value
            setText()
        }

    var trimCollapsedText: CharSequence? = null
    var trimExpandedText: CharSequence? = null
    var colorClickableText: Int? = null
    private var showTrimExpandedText = true

    private var onInterceptTrimText: ((s: SpannableStringBuilder, trimText: CharSequence?) -> Unit)? = null
        set(value) {
            field = value
            setText()
        }

    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init(attrs)
    }

    private fun init(attrs: AttributeSet? = null) {
        val a = attrs?.let { context.theme.obtainStyledAttributes(it, R.styleable.ExpandableTextView, 0, 0) }
        try {
            trimLength = a?.getInt(R.styleable.ExpandableTextView_trimLength, trimLength)?: trimLength
            trimCollapsedText = resources.getString(a?.getResourceId(R.styleable.ExpandableTextView_trimCollapsedText, R.string.read_more_show)?: R.string.read_more_show)
            trimExpandedText = resources.getString(a?.getResourceId(R.styleable.ExpandableTextView_trimExpandedText, R.string.read_more_hide)?: R.string.read_more_hide)

            val defaultColor = ContextCompat.getColor(context, R.color.link)
            colorClickableText = a?.getColor(R.styleable.ExpandableTextView_colorClickableText, defaultColor)?: defaultColor

            showTrimExpandedText = a?.getBoolean(R.styleable.ExpandableTextView_showTrimExpandedText, showTrimExpandedText)?: showTrimExpandedText
        } finally {
            a?.recycle()
        }

        setText()
    }

    private fun setText() {
        super.setText(getDisplayableText(), bufferType)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    private fun getDisplayableText(): CharSequence? {
        return getTrimmedText(customText)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        this.customText = text
        bufferType = type
        setText()
    }

    private fun getTrimmedText(text: CharSequence?): CharSequence? {
        if (text != null && text.length > trimLength) {
            if (readMore) {
                return updateCollapsedText()
            } else {
                return updateExpandedText()
            }
        }
        return text
    }

    private fun updateCollapsedText(): CharSequence {
        val trimEndIndex = trimLength + 1
        val s = SpannableStringBuilder(customText, 0, trimEndIndex)
            .append(ELLIPSIZE)
            .append(trimCollapsedText)
        return addClickableSpan(s, trimCollapsedText)
    }

    private fun updateExpandedText(): CharSequence? {
        if (showTrimExpandedText) {
            val s = SpannableStringBuilder(customText, 0, customText?.length?: 0).append(trimExpandedText)
            return addClickableSpan(s, trimExpandedText)
        }
        return customText
    }

    private fun addClickableSpan(s: SpannableStringBuilder, trimText: CharSequence?): CharSequence {
        s.setSpan(clickableSpan, s.length - (trimText?.length?: 0), s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        onInterceptTrimText?.invoke(s, trimText)

        return s
    }

    private val clickableSpan = object: ClickableSpan() {

        override fun onClick(widget: View) {
            readMore = !readMore
            setText()
        }

        override fun updateDrawState(ds: TextPaint) {
            colorClickableText?.let{ ds.color = it }
        }
    }
}