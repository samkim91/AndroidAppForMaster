package kr.co.soogong.master.presentation.ui.profile.detail.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditAddressBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.presentation.uihelper.common.KakaoAddressActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.LocationHelper
import timber.log.Timber

@AndroidEntryPoint
class EditAddressFragment : BaseFragment<FragmentEditAddressBinding>(
    R.layout.fragment_edit_address
) {
    private val viewModel: EditAddressViewModel by viewModels()
    private val editProfileContainerViewModel: EditProfileContainerViewModel by activityViewModels()

    private val getAddressLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.roadAddress.value =
                    result.data?.let { KakaoAddressActivityHelper.getAddressFromIntent(it) }
                viewModel.detailAddress.value = ""
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            ivClickable.setOnClickListener {
                getAddressLauncher.launch(
                    Intent(KakaoAddressActivityHelper.getIntent(requireContext()))
                )
            }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                viewModel.roadAddress.observe(viewLifecycleOwner) {
                    stiRoadAddress.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                }

                if (stiRoadAddress.error.isNullOrEmpty()) {
                    LocationHelper.changeAddressToLatLng(
                        context = requireContext(),
                        address = "${viewModel.roadAddress.value} ${viewModel.detailAddress.value}"
                    ).run {
                        viewModel.latitude.value = this["latitude"]
                        viewModel.longitude.value = this["longitude"]

                        viewModel.saveCompanyAddress()
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(REQUEST_SUCCESS)
            }
        })
    }

    companion object {
        private const val TAG = "EditAddressFragment"

        fun newInstance() = EditAddressFragment()
    }
}