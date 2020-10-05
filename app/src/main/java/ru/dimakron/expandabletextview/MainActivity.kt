package ru.dimakron.expandabletextview

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.dimakron.expandabletextview.utils.CustomURLSpan
import ru.dimakron.expandabletextview.utils.RoundedBackgroundSpan
import kotlin.math.roundToInt

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loremIpsum = "Lorem ipsum dolor <a href=\"https://yandex.ru/\">Ссылка</a> sit amet, consectetur adipiscing elit. Duis odio odio, feugiat condimentum purus in, lobortis commodo magna. In diam risus, cursus id molestie ut, aliquet non dolor. Vivamus vel ipsum placerat, faucibus purus in, rhoncus urna. Donec condimentum erat lacus, vel faucibus massa bibendum et. Nam fermentum tellus a tellus efficitur, in pharetra ipsum posuere. Fusce eu ultrices justo, eu accumsan felis. Donec mi massa, varius id rhoncus nec, malesuada in est. Morbi viverra lectus nec ultrices sagittis. Cras id justo mi. Nulla facilisi. Fusce sed turpis eu enim hendrerit dictum sit amet ut purus. In scelerisque turpis pellentesque ipsum fringilla, id facilisis lectus placerat. https://yandex.ru/ Quisque et tortor quis leo rutrum cursus et et libero. Pellentesque pretium erat aliquam, tincidunt urna auctor, aliquet lectus. Pellentesque a lectus in arcu varius congue a nec urna. Sed et eros vestibulum, pulvinar dui in, pretium tellus. Nullam vulputate pharetra fermentum. Nulla facilisi. Proin consectetur odio eget dictum mattis. Quisque a libero sem. Mauris dapibus lorem id orci eleifend viverra. Donec sed enim quis enim tristique pharetra at ut sem. Cras auctor in arcu nec congue. Morbi eleifend commodo porttitor. Curabitur tristique, ante quis ultricies auctor, sapien augue aliquam tortor, a lacinia odio metus ut urna. Nullam id tempor elit. Vestibulum egestas quam sit amet mi bibendum suscipit. Cras id nunc et diam pretium vestibulum nec sagittis diam. Integer mattis dui a ipsum viverra gravida nec quis magna. Vestibulum et gravida erat, vel posuere quam. Vestibulum ac purus ut arcu rutrum venenatis. Nulla iaculis orci in sollicitudin rutrum. Mauris dui quam, auctor a pulvinar eleifend, feugiat quis lorem. Duis lobortis erat nec ipsum venenatis, in consequat nunc malesuada. Aliquam erat volutpat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec feugiat purus vitae tellus tempus fermentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam suscipit, nibh nec volutpat vehicula, sapien orci congue eros, vitae aliquet erat nibh et eros. Etiam nibh massa, vulputate at sagittis at, gravida eget turpis. Maecenas neque purus, molestie eget ullamcorper a, sodales at ex. Fusce mattis vehicula leo, nec aliquet neque congue in. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed placerat risus et hendrerit mollis. Nullam lobortis placerat dolor, at ornare odio hendrerit at. Mauris metus purus, rhoncus a ultrices vel, dictum eu mauris. Morbi scelerisque nisi gravida scelerisque tincidunt. Vivamus nec pellentesque urna. Fusce commodo libero nibh, vel dictum tortor volutpat vitae. Nulla in molestie massa. Etiam vitae efficitur magna. Suspendisse augue ex, semper quis orci sit amet, facilisis lobortis elit. Vivamus lobortis ipsum nunc, faucibus posuere turpis mollis eget. Curabitur dapibus mauris risus, eget dictum lacus eleifend iaculis. Aliquam tincidunt nisl neque, ut facilisis neque vehicula nec. Maecenas malesuada diam sed est ullamcorper, viverra suscipit nisi congue. Aenean eu odio interdum, porta nibh at, vestibulum velit. Proin et augue lacus. Curabitur ut lacus et felis suscipit laoreet. Nullam enim dui, fringilla eget efficitur in, fermentum eu ante. Nam vel cursus elit. Integer luctus sed nisl eget egestas. Etiam imperdiet aliquam elit, eu fringilla diam euismod lobortis. Proin imperdiet et risus non varius. Praesent ornare mollis mauris non ultricies. Fusce quis lacinia ex. Morbi at nunc mollis, pellentesque purus non, ornare risus. Fusce facilisis sapien magna. Duis sollicitudin non nulla a malesuada. Vestibulum a cursus justo. Aenean enim dolor, tincidunt vel dapibus lacinia, euismod ac ligula. Maecenas eleifend sollicitudin viverra. Ut commodo quis eros ac lacinia. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Ut vel lobortis purus. Vivamus pharetra tincidunt arcu, et tristique odio efficitur a. Etiam sed augue erat. Proin bibendum ex facilisis justo dapibus, nec vulputate ipsum ornare. Interdum et malesuada fames ac ante ipsum primis in faucibus. Ut at scelerisque risus, eget vestibulum nisl. Duis et velit aliquet, luctus nulla eget, vulputate quam. In iaculis sit amet odio elementum pretium. Praesent eros massa, malesuada euismod turpis sed, scelerisque pretium dolor. Sed quis metus lectus. Nam non porta justo. Aliquam tristique pharetra vehicula. Morbi cursus dictum tortor sit amet imperdiet. Suspendisse non fringilla nisl."
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            expandableTextView.text = Html.fromHtml(loremIpsum)
        } else {
            expandableTextView.text = Html.fromHtml(loremIpsum, Html.FROM_HTML_MODE_LEGACY)
        }

        // Кастомизация spannable
        expandableTextView.onCustomizeSpannable = this::onCustomizeSpannable

        button1.setOnClickListener { expandableTextView.trimLength = 100 }
        button2.setOnClickListener { expandableTextView.collapsedLinkText = "Расчехляй" }
        button3.setOnClickListener { expandableTextView.expandedLinkText = "Зачехляй" }
        button4.setOnClickListener { expandableTextView.linkColor = ContextCompat.getColor(this, R.color.colorPrimary) }
    }

    private fun onCustomizeSpannable(spannable: Spannable, linkText: CharSequence) {
        spannable.setSpan(RelativeSizeSpan(0.715f), spannable.length - linkText.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(RoundedBackgroundSpan(
                ContextCompat.getColor(this, R.color.blue_f7f9ff),
                ContextCompat.getColor(this, R.color.grey_707c9b),
                resources.getDimension(R.dimen.size_6).roundToInt(),
                resources.getDimension(R.dimen.size_4)
        ),spannable.length - linkText.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val spans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
        for (oldSpan in spans) {
            val start = spannable.getSpanStart(oldSpan)
            val end = spannable.getSpanEnd(oldSpan)
            val url = oldSpan.url
            spannable.removeSpan(oldSpan)
            spannable.setSpan(CustomURLSpan(url, this::onLinkClick), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun onLinkClick(url: String){
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show()
    }
}