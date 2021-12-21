package kr.co.soogong.master.ui.requirement.action.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySearchBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.requirement.RequirementViewModel.Companion.ASK_FOR_REVIEW_SUCCESSFULLY
import kr.co.soogong.master.ui.requirement.action.search.SearchViewModel.Companion.SEARCH_REQUIREMENTS_FAILED
import kr.co.soogong.master.ui.requirement.card.RequirementCardsAdapter
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

            // 엔터 버튼 누르면 키보드 사라짐
            sbSearch.searchEditText.setOnKeyListener { _, keyCode, event ->
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) {
                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    }
                    true
                } else {
                    false
                }
            }

            requirements.adapter =
                RequirementCardsAdapter(this@SearchActivity, supportFragmentManager, viewModel)

            setSearchPeriodDropdown()
        }
    }

    private fun setSearchPeriodDropdown() {
        binding.actvItem.setAdapter(ArrayAdapter(this,
            R.layout.textview_item_dropdown,
            viewModel.spinnerItems.map { it.first }))

        binding.actvItem.setOnItemClickListener { _, _, position, _ ->
            viewModel.searchingPeriod.value = viewModel.spinnerItems[position].second
            viewModel.searchRequirements()
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.searchingText.debounce(500L, CoroutineScope(Dispatchers.Main))
            .observe(
                this,
                {        // 검색을 자동으로 실행해주는 기능인데, 글자가 바뀔 때마다 검색되면 요청이 많으니, 0.5초 간격으로 하나로 모아서 요청
                    viewModel.searchRequirements()
                })

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                SEARCH_REQUIREMENTS_FAILED -> toast(getString(R.string.error_message_of_request_failed))
                ASK_FOR_REVIEW_SUCCESSFULLY -> viewModel.searchRequirements()
                SearchViewModel.CANCEL_ACTIVITY -> onBackPressed()
            }
        })
    }

    companion object {
        private const val TAG = "SearchActivity"
    }
}