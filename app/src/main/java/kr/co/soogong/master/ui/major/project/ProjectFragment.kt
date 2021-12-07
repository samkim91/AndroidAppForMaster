package kr.co.soogong.master.ui.major.project

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.FragmentProjectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.major.project.ProjectViewModel.Companion.GET_PROJECT_FAILED
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ProjectFragment : BaseFragment<FragmentProjectBinding>(
    R.layout.fragment_project
) {
    private val viewModel: ProjectViewModel by viewModels()

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
            buttonThemeSelectingDone = ButtonTheme.Primary

            list.adapter = ProjectAdapter(itemClickListener = { project, isChecked ->
                if (isChecked) viewModel.checkedList.addToSet(project)
                else viewModel.checkedList.remove(project)
            })

            setSelectingDoneClickListener {
                viewModel.checkedList.value?.let {
                    activity?.setResult(RESULT_OK,
                        MajorActivityHelper.getIntentIncludingProjects(it))
                    activity?.finish()
                }
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
        private const val TAG = "ProjectFragment"
        private const val CATEGORY_ID = "CATEGORY_ID"

        fun newInstance(categoryId: Int) = ProjectFragment().apply {
            arguments = Bundle().apply {
                putInt(CATEGORY_ID, categoryId)
            }
        }

        fun getCategoryIdFromSavedState(savedStateHandle: SavedStateHandle) =
            savedStateHandle.getLiveData<Int>(CATEGORY_ID)
    }
}