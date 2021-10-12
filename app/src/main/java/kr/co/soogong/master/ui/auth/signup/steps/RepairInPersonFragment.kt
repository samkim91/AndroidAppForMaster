package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpRepairInPersonBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class RepairInPersonFragment : BaseFragment<FragmentSignUpRepairInPersonBinding>(
    R.layout.fragment_sign_up_repair_in_person
) {
    private val viewModel: SignUpViewModel by activityViewModels()

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

            binding.repairInPersonCheckbox.setCheckClick {
                viewModel.repairInPerson.value = repairInPersonCheckbox.checkBox.isChecked
            }

            defaultButton.setOnClickListener {
                viewModel.repairInPerson.observe(viewLifecycleOwner, {
                    alertRepairInPersonRequired.isVisible = !it
                })

                if (!alertRepairInPersonRequired.isVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    companion object {
        private const val TAG = "RepairInPersonFragment"

        fun newInstance(): RepairInPersonFragment {
            return RepairInPersonFragment()
        }
    }
}