package kr.co.soogong.master.ui.requirement.action.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySearchBinding
import kr.co.soogong.master.ui.base.BaseActivity
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
                viewModel.selectedItems.value = viewModel.spinnerItems[position]
                binding.spinner.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectedItems.value = viewModel.spinnerItems[0]
                binding.spinner.setSelection(0)
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "SearchActivity"

    }
}