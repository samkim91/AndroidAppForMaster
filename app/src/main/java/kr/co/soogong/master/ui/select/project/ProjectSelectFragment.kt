package kr.co.soogong.master.ui.select.project

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.databinding.FragmentProjectSelectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.select.project.ProjectSelectViewModel.Companion.GET_PROJECT_FAILED
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_BUSINESS_TYPE
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_CATEGORY
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_PROJECT_LIST
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProjectSelectFragment : BaseFragment<FragmentProjectSelectBinding>(
    R.layout.fragment_project_select
) {
    private val category: Category by lazy {
        arguments?.getParcelable(BUNDLE_CATEGORY) ?: Category(0, "")
    }

    @Inject
    lateinit var factory: ProjectSelectViewModel.AssistedFactory

    private val viewModel: ProjectSelectViewModel by viewModels {
        ProjectSelectViewModel.provideFactory(factory, category)
    }

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

            list.adapter = ProjectSelectAdapter(listener = { position, project ->
                viewModel.changeList(position, project.apply {
                    checked = !checked
                })
            })

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
            list.addItemDecoration(dividerItemDecoration)

            // Todo.. 차후 리팩토링 해야함. 클릭될 때마다 리스트 뷰가 업데이트 되는데, 개선 필요..
            setSelectClick {
                val extra = Bundle()
                val intent = Intent()

                extra.putParcelable(BUNDLE_BUSINESS_TYPE, BusinessType(category = category, projects = viewModel.list.value?.filter { it.checked }?.toMutableList()))
                intent.putExtras(extra)
                activity?.setResult(RESULT_OK, intent)
                activity?.finish()
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when(action) {
                GET_PROJECT_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "ProjectSelectFragment"

        fun newInstance(category: Category): ProjectSelectFragment {
            val args = Bundle()
            args.putParcelable(BUNDLE_CATEGORY, category)
            val fragment = ProjectSelectFragment()
            fragment.arguments = args
            return fragment
        }
    }
}