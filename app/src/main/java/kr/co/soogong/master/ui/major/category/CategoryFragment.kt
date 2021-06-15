package kr.co.soogong.master.ui.major.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentCategoryBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.major.MajorFragment
import kr.co.soogong.master.ui.major.category.CategoryViewModel.Companion.GET_CATEGORY_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    R.layout.fragment_category
) {
    private val viewModel: CategoryViewModel by viewModels()

    private var majorFragment: MajorFragment? = null

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

            list.adapter = CategoryAdapter(
                clickListener = { category ->
                    majorFragment?.setProjectFragment(category)
                }
            )

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
            list.addItemDecoration(dividerItemDecoration)

        }
        majorFragment = activity as? MajorFragment
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