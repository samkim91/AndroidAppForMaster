package kr.co.soogong.master.presentation.ui.preferences.account

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAccountBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.preferences.account.AccountViewModel.Companion.WITHDRAWAL
import kr.co.soogong.master.utility.EventObserver
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

            abHeader.setButtonBackClickListener { onBackPressed() }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                WITHDRAWAL -> {
                    // TODO: 2021/06/15 disabled master...

                }
//                AccountViewModel.PASSWORD -> {
//                    startActivity(
//                        ChangePasswordActivityHelper.getIntent(
//                            this@AccountActivity,
//                            FROM_MY_PAGE,
//                            ""
//                        )
//                    )
//                }
            }
        })
    }

    companion object {
        private const val TAG = "AccountActivity"
    }
}