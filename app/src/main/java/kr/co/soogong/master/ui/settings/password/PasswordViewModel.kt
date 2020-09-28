package kr.co.soogong.master.ui.settings.password

import android.text.Editable
import android.text.TextWatcher
import kr.co.soogong.master.ui.base.BaseViewModel


class PasswordViewModel : BaseViewModel() {
    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable) = Unit
    }
}