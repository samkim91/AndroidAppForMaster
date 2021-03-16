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
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.databinding.FragmentProjectSelectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_CATEGORY
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_PROJECT_LIST
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

            setSelectClick {
                val extra = Bundle()
                val intent = Intent()

                extra.putParcelable(BUNDLE_CATEGORY, category)
                val list = viewModel.list.value?.filter { it.checked }
                extra.putParcelableArray(BUNDLE_PROJECT_LIST, list?.toTypedArray())
                intent.putExtras(extra)
                activity?.setResult(RESULT_OK, intent)
                activity?.finish()
            }
        }
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