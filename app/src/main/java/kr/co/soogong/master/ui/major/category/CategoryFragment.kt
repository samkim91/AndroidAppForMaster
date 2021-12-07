package kr.co.soogong.master.ui.major.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentCategoryBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.major.MajorActivity
import kr.co.soogong.master.ui.major.category.CategoryViewModel.Companion.GET_CATEGORY_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    R.layout.fragment_category
) {
    private val viewModel: CategoryViewModel by viewModels()

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

            rvCategories.adapter = CategoryAdapter(
                itemClickListener = { category ->
                    (activity as MajorActivity).moveToProjectFragment(category = category)
                }
            )
        }
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
        private const val TAG = "CategoryFragment"

        fun newInstance(): CategoryFragment {
            val args = Bundle()

            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}