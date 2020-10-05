package ru.dimakron.expandabletextview

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.style.RelativeSizeSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimakron.expandabletextview.utils.RoundedBackgroundSpan
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            expandableTextView.text = Html.fromHtml(getString(R.string.lorem_ipsum))
        } else {
            expandableTextView.text =
                Html.fromHtml(getString(R.string.lorem_ipsum), Html.FROM_HTML_MODE_LEGACY)
        }
        //for test
        //expandableTextView.onInterceptTrimText = ::setCustomReadMoreSpan
        button1.setOnClickListener {
            expandableTextView.trimLength = 100
        }
        button2.setOnClickListener {
            expandableTextView.collapsedLinkText = "Расчехляй"
        }
        button3.setOnClickListener {
            expandableTextView.expandedLinkText = "Зачехляй"
        }
        button4.setOnClickListener {
            expandableTextView.linkColor = ContextCompat.getColor(this, R.color.colorAccent)
        }

        //TODO: not working now
        // expandableTextView.transformationMethod = LinkTransformationMethod({})
        // expandableTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setCustomReadMoreSpan(ssb: Spannable, trimText: CharSequence) {
        ssb.setSpan(
            RelativeSizeSpan(0.715f),
            ssb.length - trimText.length,
            ssb.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            RoundedBackgroundSpan(
                ContextCompat.getColor(this, R.color.blue_f7f9ff),
                ContextCompat.getColor(this, R.color.grey_707c9b),
                resources.getDimension(R.dimen.size_6).roundToInt(),
                resources.getDimension(R.dimen.size_4)
            ), ssb.length - trimText.length, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}