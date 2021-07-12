package kr.co.soogong.master.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_EMAIL
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_FLEXIBLE_COST
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_OTHER_FLEXIBLE_OPTION
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uihelper.profile.MyReviewsActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
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
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            reviewBox.setOnClickListener {
                startActivity(
                    MyReviewsActivityHelper.getIntent(requireContext())
                )
            }

            editRequiredInfo.setOnClickListener {
                startActivity(
                    EditRequiredInformationActivityHelper.getIntent(requireContext())
                )
            }

            portfolio.addDefaultButtonClickListener {
                startActivity(
                    PortfolioListActivityHelper.getIntent(requireContext(), PORTFOLIO)
                )
            }

            priceByProject.addDefaultButtonClickListener {
                startActivity(
                    PortfolioListActivityHelper.getIntent(requireContext(), PRICE_BY_PROJECTS)
                )
            }

            profileImage.addDefaultButtonClickListener {
                getSingleImage()
            }

            profileImage.addFirstButtonClickListener {
                // todo.. 삭제 기능
            }

            profileImage.addSecondButtonClickListener {
                // todo.. 수정기능.. upload할 때 기존 이미지가 있으면 삭제->등록으로 하는게 좋을 듯
                getSingleImage()
            }

            flexibleCost.addDefaultButtonClickListener {
                startActivity(
                    EditProfileContainerActivityHelper.getIntent(
                        requireContext(),
                        EDIT_FLEXIBLE_COST
                    )
                )
            }

            otherFlexibleOption.addDefaultButtonClickListener {
                startActivity(
                    EditProfileContainerActivityHelper.getIntent(
                        requireContext(),
                        EDIT_OTHER_FLEXIBLE_OPTION
                    )
                )
            }

            email.addDefaultButtonClickListener {
                startActivity(
                    EditProfileContainerActivityHelper.getIntent(
                        requireContext(),
                        EDIT_EMAIL
                    )
                )
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event){
                GET_PROFILE_FAILED, REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestProfile()
    }

    private fun getSingleImage() {
        PermissionHelper.checkImagePermission(context = requireContext(),
            onGranted = {
                TedImagePicker.with(requireContext())
                    .buttonBackground(R.drawable.shape_fill_green_background)
                    .start { uri ->
                        viewModel.profileImage.value = uri
                        viewModel.saveMasterProfileImage()
                    }
            },
            onDenied = {
                requireContext().toast(getString(R.string.permission_denied_message))
            }
        )
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