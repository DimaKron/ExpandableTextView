package ru.dimakron.expandabletextview

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            expandableTextView.text = Html.fromHtml(getString(R.string.lorem_ipsum))
        } else {
            expandableTextView.text = Html.fromHtml(getString(R.string.lorem_ipsum), Html.FROM_HTML_MODE_LEGACY)
        }
        // not working now
        // expandableTextView.transformationMethod = LinkTransformationMethod({})
        // expandableTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}