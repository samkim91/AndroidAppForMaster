package kr.co.soogong.master.ui.major.project

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.databinding.FragmentProjectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.major.project.ProjectViewModel.Companion.GET_PROJECT_FAILED
import kr.co.soogong.master.uihelper.major.MajorActivityHelper.BUNDLE_MAJOR
import kr.co.soogong.master.uihelper.major.MajorActivityHelper.BUNDLE_CATEGORY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProjectFragment : BaseFragment<FragmentProjectBinding>(
    R.layout.fragment_project
) {
    private val category: Category by lazy {
        arguments?.getParcelable(BUNDLE_CATEGORY) ?: Category(0, "")
    }

    @Inject
    lateinit var factory: ProjectViewModel.AssistedFactory

    private val viewModel: ProjectViewModel by viewModels {
        ProjectViewModel.provideFactory(factory, category)
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

            list.adapter = ProjectAdapter(listener = { position, project ->
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

                extra.putParcelable(
                    BUNDLE_MAJOR,
                    Major(
                        category = category,
                        projects = viewModel.list.value?.filter { it.checked }?.toMutableList()
                    )
                )
                intent.putExtras(extra)
                activity?.setResult(RESULT_OK, intent)
                activity?.finish()
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                GET_PROJECT_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "ProjectSelectFragment"

        fun newInstance(category: Category): ProjectFragment {
            val args = Bundle()
            args.putParcelable(BUNDLE_CATEGORY, category)
            val fragment = ProjectFragment()
            fragment.arguments = args
            return fragment
        }
    }
}