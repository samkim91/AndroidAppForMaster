package kr.co.soogong.master.ui.select.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentCategorySelectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.select.SelectFragment
import kr.co.soogong.master.ui.select.category.CategorySelectViewModel.Companion.GET_CATEGORY_FAILED
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CategorySelectFragment : BaseFragment<FragmentCategorySelectBinding>(
    R.layout.fragment_category_select
) {
    private val viewModel: CategorySelectViewModel by viewModels()

    private var selectFragment: SelectFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            list.adapter = CategorySelectAdapter(
                clickListener = { category ->
                    selectFragment?.setProjectSelectFragment(category)
                }
            )

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
            list.addItemDecoration(dividerItemDecoration)

        }
        selectFragment = activity as? SelectFragment
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                GET_CATEGORY_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "CategorySelectFragment"

        fun newInstance(): CategorySelectFragment {
            val args = Bundle()

            val fragment = CategorySelectFragment()
            fragment.arguments = args
            return fragment
        }
    }
}