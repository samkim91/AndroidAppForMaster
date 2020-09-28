package kr.co.soogong.master.ui.settings.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPasswordBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class PasswordActivity : BaseActivity<ActivityPasswordBinding>(
    R.layout.activity_password
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            with(actionBar) {
                title.text = "비밀번호 변경"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            val watcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
                    Unit

                override fun afterTextChanged(s: Editable) = Unit
            }

            inputPassword.addTextChangedListener(watcher)
            inputPasswordCheck.addTextChangedListener(watcher)

            setChangeClick {
                val password1 = inputPassword.text
                val password2 = inputPasswordCheck.text

                if (password1 != password2) {
                    inputPasswordCheck.hintVisible = true
                } else {
                    inputPassword.hintVisible = false
                    inputPasswordCheck.hintVisible = false
                }
            }


        }
    }

    companion object {
        private const val TAG = "PasswordActivity"
    }
}