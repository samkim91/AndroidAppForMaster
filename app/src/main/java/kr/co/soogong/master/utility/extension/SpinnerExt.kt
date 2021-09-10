@file:JvmName("SpinnerExt")

package kr.co.soogong.master.utility.extension

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("setItems")
fun Spinner.setItems(items: List<String>) {
    this.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items)
}