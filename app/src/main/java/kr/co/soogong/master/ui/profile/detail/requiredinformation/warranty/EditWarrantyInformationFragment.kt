package kr.co.soogong.master.ui.profile.detail.requiredinformation.warranty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditWarrantyInformationBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.profile.detail.requiredinformation.warranty.EditWarrantyInformationViewModel.Companion.GET_WARRANTY_INFORMATION_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.warranty.EditWarrantyInformationViewModel.Companion.SAVE_WARRANTY_INFORMATION_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.warranty.EditWarrantyInformationViewModel.Companion.SAVE_WARRANTY_INFORMATION_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditWarrantyInformationFragment : BaseFragment<FragmentEditWarrantyInformationBinding>(
    R.layout.fragment_edit_warranty_information
) {
    private val viewModel: EditWarrantyInformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestWarrantyInformation()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            warrantyPeriod.addDropdownClickListener {
                Timber.tag(TAG).w("Dropdown Clicked")
                val bottomDialog =
                    BottomDialogRecyclerView.newInstance(
                        title = BottomDialogData.insertingWarrantyPeriodTitle,
                        dialogData = BottomDialogData.getWarrantyPeriodList(),
                        itemClick = { _, value ->
                            viewModel.warrantyPeriod.value = value
                        }
                    )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
            }

            defaultButton.setOnClickListener {
                viewModel.warrantyPeriod.observe(viewLifecycleOwner, {
                    warrantyPeriod.alertVisible = it == null || it == 0
                })

                viewModel.warrantyDescription.observe(viewLifecycleOwner, {
                    warrantyDescription.alertVisible = it.isNullOrEmpty()
                })

                if (!warrantyPeriod.alertVisible && !warrantyDescription.alertVisible) viewModel.saveWarrantyInfo()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_WARRANTY_INFORMATION_SUCCESSFULLY -> activity?.onBackPressed()
                SAVE_WARRANTY_INFORMATION_FAILED, GET_WARRANTY_INFORMATION_FAILED -> requireContext().toast(
                    getString(R.string.error_message_of_request_failed)
                )
            }
        })
    }

    companion object {
        private const val TAG = "EditWarrantyInformationFragment"

        fun newInstance() = EditWarrantyInformationFragment()
    }
}