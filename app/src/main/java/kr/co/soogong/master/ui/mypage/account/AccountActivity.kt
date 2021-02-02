package kr.co.soogong.master.ui.mypage.account

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAccountBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.mypage.password.PasswordActivityHelper
import timber.log.Timber

class AccountActivity : BaseActivity<ActivityAccountBinding>(
    R.layout.activity_account
) {
    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@AccountActivity

            with(actionBar) {
                title.text = getString(R.string.account_page_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setPasswordChangeClick {
                Timber.tag(TAG).i("initLayout: Password Change Button")
                startActivity(PasswordActivityHelper.getIntent(this@AccountActivity))
            }

            setWithdrawalClick {
                Timber.tag(TAG).i("initLayout: WithDraw Button")
                vm.withDraw()
            }
        }
    }

    companion object {
        private const val TAG = "AccountActivity"
    }
}