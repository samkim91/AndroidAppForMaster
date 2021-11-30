package kr.co.soogong.master.atomic.atoms

import android.widget.ArrayAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

interface IDropdownMenu {
    val textInputLayout: TextInputLayout
    val autoCompleteTextView: MaterialAutoCompleteTextView
    var adapter: ArrayAdapter<Any>?
}