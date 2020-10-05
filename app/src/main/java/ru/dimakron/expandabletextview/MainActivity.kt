package ru.dimakron.expandabletextview

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimakron.expandabletextview.utils.LinkTransformationMethod

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expandableTextView.text = getString(R.string.lorem_ipsum)
        expandableTextView.transformationMethod = LinkTransformationMethod({})
        expandableTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}