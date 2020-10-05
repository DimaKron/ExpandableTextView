package ru.dimakron.expandabletextview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expandableTextView.text = getString(R.string.lorem_ipsum)
        //not working now
        //expandableTextView.transformationMethod = LinkTransformationMethod({})
        //expandableTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}