package kr.co.soogong.master.ui.mypage.account

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAccountBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.mypage.password.PasswordActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class AccountActivity : BaseActivity<ActivityAccountBinding>(
    R.layout.activity_account
) {
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
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
                viewModel.withDraw()
            }
        }
    }

    companion object {
        private const val TAG = "AccountActivity"
    }
}