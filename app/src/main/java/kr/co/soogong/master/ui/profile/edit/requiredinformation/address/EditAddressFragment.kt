package kr.co.soogong.master.ui.profile.edit.requiredinformation.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditAddressBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.address.EditAddressViewModel.Companion.SAVE_COMPANY_ADDRESS_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.address.EditAddressViewModel.Companion.SAVE_COMPANY_ADDRESS_SUCCESSFULLY
import kr.co.soogong.master.ui.utils.LocationHelper
import kr.co.soogong.master.uiinterface.auth.signup.AddressActivityHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditAddressFragment : BaseFragment<FragmentEditAddressBinding>(
    R.layout.fragment_edit_address
) {
    private val viewModel: EditAddressViewModel by viewModels()

    private val getAddressLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.mainAddress.value =
                    result.data?.extras?.getString(AddressActivityHelper.ADDRESS).toString()
                viewModel.subAddress.value = ""
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.getCompanyAddress()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            address.setButtonClickListener {
                getAddressLauncher.launch(
                    Intent(AddressActivityHelper.getIntent(
                    requireContext()))
                )
            }

            defaultButton.setOnClickListener {
                viewModel.mainAddress.observe(viewLifecycleOwner, {
                    address.alertVisible = it.isNullOrEmpty()
                })

                if (!address.alertVisible) {
                    val latlng = LocationHelper.changeAddressToLatLng(requireContext(),
                        "${viewModel.mainAddress.value} ${viewModel.subAddress.value}")
                    viewModel.latitude.value = latlng["latitude"]
                    viewModel.longitude.value = latlng["longitude"]

                    viewModel.saveCompanyAddress()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event) {
                SAVE_COMPANY_ADDRESS_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_COMPANY_ADDRESS_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditAddressFragment"

        fun newInstance() = EditAddressFragment()
    }
}