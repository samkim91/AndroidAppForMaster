package kr.co.soogong.master.ui.sign.find

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityFindInfoBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.sign.find.FindInfoViewModel.Companion.FAIL_TO_CONTACT
import kr.co.soogong.master.ui.sign.find.FindInfoViewModel.Companion.FAIL_TO_NAME
import kr.co.soogong.master.ui.sign.find.FindInfoViewModel.Companion.SUCCESS_TO_FIND_INFO
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class FindInfoActivity : BaseActivity<ActivityFindInfoBinding>(
    R.layout.activity_find_info
) {
    private val viewModel: FindInfoViewModel by lazy {
        ViewModelProvider(this).get(FindInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@FindInfoActivity

            with(actionBar) {
                title.text = getString(R.string.find_info_activity_name)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setFindInfoClick {
                val email = inputEmail.text?.toString()
                viewModel.findInfo(email)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.event.observe(this, EventObserver { (event, message) ->
            when (event) {
                FAIL_TO_NAME -> Unit
                FAIL_TO_CONTACT -> Unit
                SUCCESS_TO_FIND_INFO -> Unit
            }
        })
    }

    companion object {
        private const val TAG = "FindInfoActivity"
    }
}