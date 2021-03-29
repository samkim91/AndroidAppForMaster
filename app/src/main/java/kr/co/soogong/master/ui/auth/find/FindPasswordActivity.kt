package kr.co.soogong.master.ui.auth.find

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityFindPasswordBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>(
    R.layout.activity_find_password
) {
    private val viewModel: FindPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@FindPasswordActivity

            with(actionBar) {
                title.text = getString(R.string.find_info_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            //TODO : 성공 실패에 대한 액션 처리 안 되어 있음...
            when (event) {
                FindPasswordViewModel.FAIL -> Unit
                FindPasswordViewModel.SUCCESS -> Unit
            }
        })
    }

    companion object {
        private const val TAG = "FindInfoActivity"
    }
}