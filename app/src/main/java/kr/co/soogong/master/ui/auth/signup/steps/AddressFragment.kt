package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpAddressBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_ADDRESS
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.auth.signup.AddressActivityHelper
import kr.co.soogong.master.utility.LocationHelper
import timber.log.Timber

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentSignUpAddressBinding>(
    R.layout.fragment_sign_up_address
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private val getAddressLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.roadAddress.value =
                    result.data?.extras?.getString(AddressActivityHelper.ADDRESS).toString()
                viewModel.detailAddress.value = ""
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            roadAddressTextInputClickListener = View.OnClickListener {
                getAddressLauncher.launch(AddressActivityHelper.getIntent(requireContext()))
            }
        }
    }

    private fun registerEventObserver() {
        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_ADDRESS) {
                viewModel.roadAddress.observe(viewLifecycleOwner, {
                    binding.stiRoadAddress.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                if (binding.stiRoadAddress.error.isNullOrEmpty()) {
                    LocationHelper.changeAddressToLatLng(
                        context = requireContext(),
                        address = "${viewModel.roadAddress.value} ${viewModel.detailAddress.value}"
                    ).run {
                        viewModel.latitude.value = this["latitude"]
                        viewModel.longitude.value = this["longitude"]

                        viewModel.moveToNext()
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "AddressFragment"

        fun newInstance(): AddressFragment {
            return AddressFragment()
        }
    }
}