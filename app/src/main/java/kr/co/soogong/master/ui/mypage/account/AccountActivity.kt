package kr.co.soogong.master.ui.mypage.account

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAccountBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.auth.password.ChangePasswordActivityHelper
import kr.co.soogong.master.uiinterface.auth.password.ChangePasswordActivityHelper.FROM_MY_PAGE
import kr.co.soogong.master.util.EventObserver
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
        registerEventObserve()
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
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                AccountViewModel.WITHDRAW -> {
                    // TODO : 서버 작업 필요 요청값과 줄 수 있는 값이 다름
                    Unit
                }
                AccountViewModel.PASSWORD -> {
                    startActivity(
                        ChangePasswordActivityHelper.getIntent(
                            this@AccountActivity,
                            FROM_MY_PAGE,
                            ""
                        )
                    )
                }
            }
        })
    }

    companion object {
        private const val TAG = "AccountActivity"
    }
}