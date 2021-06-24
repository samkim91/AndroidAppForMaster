package kr.co.soogong.master.ui.profile.detail.requiredinformation.major

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.databinding.FragmentEditMajorBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.requiredinformation.major.EditMajorViewModel.Companion.GET_MAJOR_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.major.EditMajorViewModel.Companion.SAVE_MAJOR_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.major.EditMajorViewModel.Companion.SAVE_MAJOR_SUCCESSFULLY
import kr.co.soogong.master.uihelper.major.MajorActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.MajorChipGroupHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditMajorFragment : BaseFragment<FragmentEditMajorBinding>(
    R.layout.fragment_edit_major
) {
    private val viewModel: EditMajorViewModel by viewModels()

    private var getMajorLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedMajor: Major by lazy {
                    data?.getParcelableExtra(MajorActivityHelper.BUNDLE_MAJOR)
                        ?: Major(null, null)
                }
                MajorChipGroupHelper.makeEntryChipGroupWithSubtitleForMajor(
                    layoutInflater = layoutInflater,
                    container = binding.majorContainer,
                    newMajor = selectedMajor,
                    viewModelBusinessTypes = viewModel.major
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestMajor()
        MajorChipGroupHelper.addMajorToContainer(
            layoutInflater = layoutInflater,
            container = binding.majorContainer,
            majorList = viewModel.major
        )
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            major.setButtonClickListener {
                getMajorLauncher.launch(
                    Intent(
                        MajorActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.major.observe(viewLifecycleOwner, {
                    major.alertVisible = it.isNullOrEmpty()
                })

                if (!major.alertVisible) viewModel.saveMajor()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MAJOR_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_MAJOR_FAILED, GET_MAJOR_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditMajorFragment"

        fun newInstance() = EditMajorFragment()
    }
}