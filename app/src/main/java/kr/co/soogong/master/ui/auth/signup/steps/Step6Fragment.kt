package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep6Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.LocationHelper
import kr.co.soogong.master.uiinterface.auth.signup.AddressActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class Step6Fragment : BaseFragment<FragmentSignUpStep6Binding>(
    R.layout.fragment_sign_up_step6
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private var getAddressLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.address.value =
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
                getAddressLauncher.launch(Intent(AddressActivityHelper.getIntent(
                    requireContext())))
            }

            defaultButton.setOnClickListener {
                viewModel.address.observe(viewLifecycleOwner, {
                    address.alertVisible = it.isNullOrEmpty()
                })
                viewModel.subAddress.observe(viewLifecycleOwner, {
                    address.alertVisible = it.isNullOrEmpty()
                })

                if (!address.alertVisible) {
                    val latlng = LocationHelper.changeAddressToLatLng(requireContext(),
                        "${viewModel.address.value} ${viewModel.subAddress.value}")
                    viewModel.latitude.value = latlng["latitude"]
                    viewModel.longitude.value = latlng["longitude"]

                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "Step6Fragment"

        fun newInstance(): Step6Fragment {
            return Step6Fragment()
        }
    }
}