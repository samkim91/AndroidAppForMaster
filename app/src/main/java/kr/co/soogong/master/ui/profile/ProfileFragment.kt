package kr.co.soogong.master.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogBundle
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.GET_PROFILE_FAILED
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.*
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_EMAIL
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_FLEXIBLE_COST
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_OTHER_FLEXIBLE_OPTION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.FREE_MEASURE
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PRICE_BY_PROJECTS
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
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
            buttonThemeRequestReview = ButtonTheme.OutlinedPrimary
            colorThemeProfileGuideline = ColorTheme.Grey

            reviewBox.setOnClickListener {
                startActivity(
                    MyReviewsActivityHelper.getIntent(requireContext())
                )
            }

//            showProfileButton.setOnClickListener {
//                startActivity(
//                    ShowMyProfileInWebHelper.getIntent(viewModel.profile.value?.uid)
//                )
//            }

            bbRequestReview.onButtonClick = View.OnClickListener {
                BottomDialogRecyclerView.newInstance(
                    dialogBundle = BottomDialogBundle.getRequestingReviewBundle(),
                    itemClick = { wayOfRequesting, _ ->
                        Timber.tag(TAG).d("requestReviewButton: $wayOfRequesting is clicked")
                        RequestReviewHelper.requestReviewBySharing(
                            requireContext(),
                            viewModel.profile.value?.uid,
                            viewModel.profile.value?.representativeName,
                            wayOfRequesting
                        )
                    }
                ).let {
                    it.show(parentFragmentManager, it.tag)
                }
            }

            editRequiredInfo.setOnClickListener {
                startActivity(
                    EditRequiredInformationActivityHelper.getIntent(requireContext())
                )
            }

            freeMeasure.setOnClickListener {
                startActivity(
                    EditProfileContainerActivityHelper.getIntent(
                        requireContext(),
                        FREE_MEASURE
                    )
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
                // todo.. 삭제 기능 추가 필요
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
            when (event) {
                GET_PROFILE_FAILED, REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                DISMISS_LOADING -> dismissLoading()
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
                    .buttonBackground(R.drawable.shape_green_background_radius8)
                    .start { uri ->
                        if (FileHelper.isImageExtension(uri, requireContext()) == false) {
                            requireContext().toast(getString(R.string.invalid_image_extension))
                            return@start
                        }

                        viewModel.profileImage.value = uri

                        if (viewModel.profile.value?.approvedStatus == ApprovedCodeTable.code) {
                            CustomDialog.newInstance(
                                DialogData.getConfirmingForRequiredDialogData(requireContext()))
                                .let {
                                    it.setButtonsClickListener(
                                        onPositive = {
                                            showLoading(parentFragmentManager)
                                            viewModel.saveMasterProfileImage()
                                        },
                                        onNegative = { }
                                    )
                                    it.show(parentFragmentManager, it.tag)
                                }
                        } else {
                            showLoading(parentFragmentManager)
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