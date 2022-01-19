package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentOwnerNameBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_OWNER_NAME
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class OwnerNameFragment : BaseFragment<FragmentOwnerNameBinding>(
    R.layout.fragment_owner_name
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

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
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")
        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_OWNER_NAME) {
                viewModel.ownerName.observe(viewLifecycleOwner, {
                    binding.stiOwnerName.error =
                        if (it.isNullOrEmpty()) getString(R.string.required_field_alert) else null
                })

                if (binding.stiOwnerName.error.isNullOrEmpty()) viewModel.moveToNext()
            }
        })
    }

    companion object {
        private const val TAG = "OwnerNameFragment"

        fun newInstance() = OwnerNameFragment()
    }
}