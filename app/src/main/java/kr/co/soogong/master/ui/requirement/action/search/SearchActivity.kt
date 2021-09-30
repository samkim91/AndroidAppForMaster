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
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
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

            searchBar.setCancelIconClickListener {
                viewModel.searchingText.value = ""
            }

            searchBar.setCancelTextClickListener {
                super.onBackPressed()
            }

            setSpinnerSelectedListener()

            requirements.adapter = ReceivedAdapter(
                cardClickListener = { requirementId ->
                    startActivity(
                        ViewRequirementActivityHelper.getIntent(this@SearchActivity, requirementId)
                    )
                },
                leftButtonClick = { _, _ -> },
                rightButtonClick = { requirementId, _ ->
                    startActivity(
                        EndRepairActivityHelper.getIntent(
                            this@SearchActivity,
                            requirementId
                        )
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
        viewModel.searchingText.observe(this, {     // 검색단어 삭제 버튼 활성화 여부
            binding.searchBar.cancelIconVisibility = it.isNotEmpty()
        })

        viewModel.searchingText.debounce(500L, CoroutineScope(Dispatchers.Main))
            .observe(this, {        // 검색을 자동으로 실행해주는 기능인데, 글자가 바뀔 때마다 검색되면 요청이 많으니, 0.5초 간격으로 하나로 모아서 요청
                viewModel.searchRequirements()
            })

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                SEARCH_REQUIREMENTS_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "SearchActivity"

    }
}