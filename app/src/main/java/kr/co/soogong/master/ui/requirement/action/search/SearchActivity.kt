package kr.co.soogong.master.ui.requirement.action.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySearchBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.action.search.SearchViewModel.Companion.SEARCH_REQUIREMENTS_FAILED
import kr.co.soogong.master.ui.requirement.received.ReceivedAdapter
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.debounce
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(
    R.layout.activity_search
) {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            lifecycleOwner = this@SearchActivity
            vm = viewModel

            searchBar.setCancelTextClickListener {
                super.onBackPressed()
            }

            setSpinnerSelectedListener()

            requirements.adapter = ReceivedAdapter(
                cardClickListener = { requirementId ->
                    startActivity(
                        ViewRequirementActivityHelper.getIntent(this@SearchActivity, requirementId)
                    )
                }
            )
        }
    }

    private fun setSpinnerSelectedListener() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Timber.tag(TAG).d("selected Item: $position")
                viewModel.searchingPeriod.value = getSearchingPeriod(periods[position])
                binding.spinner.setSelection(position)
                viewModel.searchRequirements()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.searchingPeriod.value = getSearchingPeriod(periods[0])
                binding.spinner.setSelection(0)
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.searchingText.debounce(500L, CoroutineScope(Dispatchers.Main))
            .observe(this, {
                viewModel.searchRequirements()
            })

        viewModel.action.observe(this, EventObserver { action ->
            when(action) {
                SEARCH_REQUIREMENTS_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "SearchActivity"

    }
}