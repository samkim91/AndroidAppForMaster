@file:JvmName("SpinnerExt")

package kr.co.soogong.master.utility.extension

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R

@BindingAdapter("setItems")
fun Spinner.setItems(items: List<String>) {
    this.adapter = ArrayAdapter(context, R.layout.basic_spinner_item, items)
}