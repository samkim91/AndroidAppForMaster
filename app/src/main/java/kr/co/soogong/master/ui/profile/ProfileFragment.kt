package kr.co.soogong.master.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.EditProfileWithCardActivity.Companion.PORTFOLIO
import kr.co.soogong.master.ui.profile.edit.EditProfileWithCardActivity.Companion.PRICE_BY_PROJECTS
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()

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

            portfolio.addDefaultButtonClickListener{
                startActivity(Intent(EditProfileWithCardActivityHelper.getIntent(requireContext(), PORTFOLIO)))
            }



            priceByProject.addDefaultButtonClickListener{
                startActivity(Intent(EditProfileWithCardActivityHelper.getIntent(requireContext(), PRICE_BY_PROJECTS)))
            }


        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestUserProfile()
    }

    companion object {
        private const val TAG = "ProfileFragment"
        fun newInstance(): ProfileFragment {
            val args = Bundle()

            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}