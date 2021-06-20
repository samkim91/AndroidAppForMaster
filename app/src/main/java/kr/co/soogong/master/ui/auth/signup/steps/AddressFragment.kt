package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpAddressBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.uihelper.auth.signup.AddressActivityHelper
import kr.co.soogong.master.utility.LocationHelper
import timber.log.Timber

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentSignUpAddressBinding>(
    R.layout.fragment_sign_up_address
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private val getAddressLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.roadAddress.value =
                    result.data?.extras?.getString(AddressActivityHelper.ADDRESS).toString()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            address.setButtonClickListener {
                getAddressLauncher.launch(
                    Intent(
                        AddressActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.roadAddress.observe(viewLifecycleOwner, {
                    address.alertVisible = it.isNullOrEmpty()
                })

                if (!address.alertVisible) {
                    val latlng = LocationHelper.changeAddressToLatLng(
                        requireContext(),
                        "${viewModel.roadAddress.value} ${viewModel.detailAddress.value}"
                    )
                    viewModel.latitude.value = latlng["latitude"]
                    viewModel.longitude.value = latlng["longitude"]

                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "AddressFragment"

        fun newInstance(): AddressFragment {
            return AddressFragment()
        }
    }
}