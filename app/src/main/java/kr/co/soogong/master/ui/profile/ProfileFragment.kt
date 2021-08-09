package kr.co.soogong.master.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.*
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_EMAIL
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_FLEXIBLE_COST
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_OTHER_FLEXIBLE_OPTION
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PRICE_BY_PROJECTS
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

            showProfileButton.setOnClickListener {
                startActivity(
                    ShowMyProfileInWebHelper.getIntent(viewModel.profile.value?.uid)
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

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestProfile()
    }

    private fun getSingleImage() {
        PermissionHelper.checkImagePermission(context = requireContext(),
            onGranted = {
                TedImagePicker.with(requireContext())
                    .buttonBackground(R.drawable.shape_fill_green_background)
                    .start { uri ->
                        viewModel.profileImage.value = uri

                        if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                            val dialog = CustomDialog(
                                dialogData = DialogData.getConfirmingForRequiredDialogData(requireContext()),
                                yesClick = { viewModel.saveMasterProfileImage() },
                                noClick = { })

                            dialog.show(parentFragmentManager, dialog.tag)
                        } else {
                            viewModel.saveMasterProfileImage()
                        }
                    }
            },
            onDenied = { }
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