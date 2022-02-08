package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.domain.entity.common.ColorTheme
import kr.co.soogong.master.databinding.FragmentRepairInPersonBinding
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_REPAIR_IN_PERSON
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class RepairInPersonFragment : BaseFragment<FragmentRepairInPersonBinding>(
    R.layout.fragment_repair_in_person
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