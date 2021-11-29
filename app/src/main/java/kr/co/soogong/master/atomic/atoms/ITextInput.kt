package kr.co.soogong.master.atomic.atoms

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface ITextInput {
    val textInputLayout: TextInputLayout
    val textInputEditText: TextInputEditText
    var enabled: Boolean?
    var maxCount: Int?
}