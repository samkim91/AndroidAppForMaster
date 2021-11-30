package kr.co.soogong.master.atomic.atoms

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface ITextInput {
    val textInputLayout: TextInputLayout
    val textInputEditText: TextInputEditText
    var error: String?
    var hint: String?
    var helper: String?
    var enabled: Boolean?
    var maxCount: Int?
}