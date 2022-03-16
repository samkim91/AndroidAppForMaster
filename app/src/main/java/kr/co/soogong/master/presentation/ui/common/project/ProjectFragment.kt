package kr.co.soogong.master.presentation.ui.common.project

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProjectBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.project.ProjectViewModel.Companion.GET_PROJECT_FAILED
import kr.co.soogong.master.presentation.uihelper.common.MajorActivityHelper
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

            list.adapter = ProjectAdapter(itemClickListener = { project, item ->
                if (item.isChecked) {
                    when {
                        viewModel.maxNumber == 0 || viewModel.maxNumber > viewModel.checkedList.value?.size!! ->
                            viewModel.checkedList.add(project)
                        else -> {
                            requireContext().toast(getString(R.string.choose_max_number, viewModel.maxNumber))
                            item.isChecked = !item.isChecked
                        }
                    }
                } else viewModel.checkedList.remove(project)
            })

            setSelectingDoneClickListener {
                viewModel.checkedList.value?.let { projects ->
                    if (projects.isEmpty()) {
                        requireContext().toast(getString(R.string.select_projects))
                        return@setSelectingDoneClickListener
                    }

                    activity?.setResult(RESULT_OK,
                        MajorActivityHelper.getIntentIncludingProjects(projects))
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
        private const val MAX_NUMBER = "MAX_NUMBER"

        fun newInstance(categoryId: Int, maxNumber: Int) = ProjectFragment().apply {
            arguments = bundleOf(
                CATEGORY_ID to categoryId,
                MAX_NUMBER to maxNumber
            )
        }

        fun getMaxNumberFromSavedState(savedStateHandle: SavedStateHandle) =
            savedStateHandle.get<Int>(MAX_NUMBER)!!

        fun getCategoryIdFromSavedState(savedStateHandle: SavedStateHandle) =
            savedStateHandle.getLiveData<Int>(CATEGORY_ID)
    }
}