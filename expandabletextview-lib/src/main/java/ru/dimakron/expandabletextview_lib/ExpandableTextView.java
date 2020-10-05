package ru.dimakron.expandabletextview_lib;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

public class ExpandableTextView extends AppCompatTextView {

    private static final int DEFAULT_TRIM_LENGTH = 240;
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final String ELLIPSIZE = "... ";

    private CharSequence text;
    private BufferType bufferType;
    private boolean readMore = true;
    private int trimLength;
    private CharSequence trimCollapsedText;
    private CharSequence trimExpandedText;
    private ReadMoreClickableSpan viewMoreSpan;
    private int colorClickableText;
    private boolean showTrimExpandedText;

    private TrimTextInterceptor trimTextInterceptor;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);

        int resourceIdTrimCollapsedText = typedArray.getResourceId(R.styleable.ExpandableTextView_trimCollapsedText, R.string.read_more_show);
        int resourceIdTrimExpandedText = typedArray.getResourceId(R.styleable.ExpandableTextView_trimExpandedText, R.string.read_more_hide);

        this.trimCollapsedText = getResources().getString(resourceIdTrimCollapsedText);
        this.trimExpandedText = getResources().getString(resourceIdTrimExpandedText);
        this.colorClickableText = typedArray.getColor(R.styleable.ExpandableTextView_colorClickableText, ContextCompat.getColor(context, R.color.link));
        this.showTrimExpandedText = typedArray.getBoolean(R.styleable.ExpandableTextView_showTrimExpandedText, DEFAULT_SHOW_TRIM_EXPANDED_TEXT);
        typedArray.recycle();
        viewMoreSpan = new ReadMoreClickableSpan();
        setText();
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text;
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (text != null && text.length() > trimLength) {
            if (readMore) {
                return updateCollapsedText();
            } else {
                return updateExpandedText();
            }
        }
        return text;
    }

    private CharSequence updateCollapsedText() {
        int trimEndIndex = trimLength + 1;
        SpannableStringBuilder s = new SpannableStringBuilder(text, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(trimCollapsedText);
        return addClickableSpan(s, trimCollapsedText);
    }

    private CharSequence updateExpandedText() {
        if (showTrimExpandedText) {
            SpannableStringBuilder s = new SpannableStringBuilder(text, 0, text.length()).append(trimExpandedText);
            return addClickableSpan(s, trimExpandedText);
        }
        return text;
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {
        s.setSpan(viewMoreSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(trimTextInterceptor != null) trimTextInterceptor.interceptTrimText(s, trimText);

        return s;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        setText();
    }

    public void setColorClickableText(int colorClickableText) {
        this.colorClickableText = colorClickableText;
    }

    public void setTrimCollapsedText(CharSequence trimCollapsedText) {
        this.trimCollapsedText = trimCollapsedText;
    }

    public void setTrimExpandedText(CharSequence trimExpandedText) {
        this.trimExpandedText = trimExpandedText;
    }

    public void setTrimTextInterceptor(TrimTextInterceptor trimTextInterceptor){
        this.trimTextInterceptor = trimTextInterceptor;
        setText();
    }

    public interface TrimTextInterceptor{
        void interceptTrimText(SpannableStringBuilder s, CharSequence trimText);
    }

    private class ReadMoreClickableSpan extends ClickableSpan {
        @Override
        public void onClick(@NotNull View widget) {
            readMore = !readMore;
            setText();
        }

        @Override
        public void updateDrawState(@NotNull TextPaint ds) {
            ds.setColor(colorClickableText);
        }
    }
}