package kr.co.soogong.master.presentation.ui.requirement.list.done

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityDoneBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.main.MainViewModel
import kr.co.soogong.master.presentation.ui.requirement.card.RequirementCardsAdapter
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class DoneActivity : BaseActivity<ActivityDoneBinding>(
    R.layout.activity_done
) {

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: DoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")

        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@DoneActivity
            vm = viewModel

            abHeader.setButtonBackClickListener { onBackPressed() }

            rvRequirements.adapter = RequirementCardsAdapter(this@DoneActivity,
                supportFragmentManager,
                mainViewModel,
                viewModel
            )
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                RequirementsViewModel.REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "DoneActivity"

    }
}