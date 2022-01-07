package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.databinding.FragmentSignUpRepairInPersonBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_REPAIR_IN_PERSON
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class RepairInPersonFragment : BaseFragment<FragmentSignUpRepairInPersonBinding>(
    R.layout.fragment_sign_up_repair_in_person
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
            colorThemeProfileGuideline = ColorTheme.Grey
        }
    }

    private fun registerEventObserver() {
        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_REPAIR_IN_PERSON) {
                viewModel.repairInPerson.observe(viewLifecycleOwner, {
                    binding.tvAlert.isVisible = !it
                })

                if (!binding.tvAlert.isVisible) viewModel.moveToNext()
            }
        })
    }

    companion object {
        private const val TAG = "RepairInPersonFragment"

        fun newInstance() = RepairInPersonFragment()
    }
}