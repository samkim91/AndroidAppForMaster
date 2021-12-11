package kr.co.soogong.master.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogBundle
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogRecyclerView
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.*
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_ADDRESS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_UNIT_INFORMATION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_INTRODUCTION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_MAJOR
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PHONE_NUMBER
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_SHOP_IMAGES
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_WARRANTY_INFORMATION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.FREE_MEASURE
import kr.co.soogong.master.utility.*
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()

    private val naverMapHelper: NaverMapHelper by lazy {
        NaverMapHelper(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            frameLayout = binding.hbmServiceArea.flMapContainer,
            coordinate = null,
            radius = null,
        )
    }

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
            colorThemeRequiredInformationProgress = ColorTheme.Red
            colorThemeOptionalInformationProgress = ColorTheme.Grey

            rbMyReviews.setOnClickListener {
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
                BottomSheetDialogRecyclerView.newInstance(
                    sheetDialogBundle = BottomSheetDialogBundle.getRequestingReviewBundle()
                ).let {
                    it.setItemClickListener { dialogItem ->
                        Timber.tag(TAG).d("requestReviewButton: ${dialogItem.key} is clicked")
                        RequestReviewHelper.requestReviewBySharing(
                            requireContext(),
                            viewModel.profile.value?.uid,
                            viewModel.profile.value?.representativeName,
                            dialogItem.value
                        )
                    }
                    it.show(parentFragmentManager, it.tag)
                }
            }

            hbcOwnerName.onButtonClick = View.OnClickListener {
                with(hbcOwnerName.tvContent) {      // 대표자명 수정을 위해, 포커스와 키보드 보이게 처리
                    this.isVisible = true
                    this.isEnabled = true
                    this.requestFocus()
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .showSoftInput(this, 0)
                }
            }

            hbcCompanyIntroduction.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_INTRODUCTION) }

            hieShopImages.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_SHOP_IMAGES) }

            hbcBusinessUnitInformation.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_BUSINESS_UNIT_INFORMATION) }

            hbcWarrantyInformation.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_WARRANTY_INFORMATION) }

            hbcContactInformation.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_PHONE_NUMBER) }

            hbcgProjects.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_MAJOR) }

            hbcCareer.onButtonClick = View.OnClickListener {
                BottomSheetDialogRecyclerView.newInstance(
                    sheetDialogBundle = BottomSheetDialogBundle.getCareerYearBundle()
                ).let {
                    it.setItemClickListener { dialogItem ->
                        if (viewModel.profile.value?.approvedStatus == CodeTable.APPROVED.code) {
                            CustomDialog.newInstance(DialogData.getConfirmingForRequiredDialogData(
                                requireContext()))
                                .let { dialog ->
                                    dialog.setButtonsClickListener(
                                        onPositive = { viewModel.saveCareerPeriod(dialogItem.value) },
                                        onNegative = { }
                                    )
                                    dialog.show(parentFragmentManager, it.tag)
                                }
                        } else {
                            viewModel.saveCareerPeriod(dialogItem.value)
                        }
                    }
                    it.show(parentFragmentManager, it.tag)
                }
            }

            hbcServiceAddress.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_ADDRESS) }

            hbmServiceArea.onButtonClick = View.OnClickListener {
                BottomSheetDialogRecyclerView.newInstance(
                    sheetDialogBundle = BottomSheetDialogBundle.getServiceAreaBundle()
                ).let {
                    it.setItemClickListener { dialogItem ->
                        viewModel.profile.value?.requiredInformation?.serviceArea = dialogItem.value
                        viewModel.saveServiceArea()
                    }
                    it.show(parentFragmentManager, it.tag)
                }
            }

            hbcFreeMeasure.onButtonClick =
                View.OnClickListener { startActivityCommonCode(FREE_MEASURE) }

//            editRequiredInfo.setOnClickListener {
//                startActivity(
//                    EditRequiredInformationActivityHelper.getIntent(requireContext())
//                )
//            }
//
//            portfolio.addDefaultButtonClickListener {
//                startActivity(
//                    PortfolioListActivityHelper.getIntent(requireContext(), PORTFOLIO)
//                )
//            }
//
//            priceByProject.addDefaultButtonClickListener {
//                startActivity(
//                    PortfolioListActivityHelper.getIntent(requireContext(), PRICE_BY_PROJECTS)
//                )
//            }
//
//            profileImage.addDefaultButtonClickListener {
//                getSingleImage()
//            }
//
//            profileImage.addFirstButtonClickListener {
//                // todo.. 삭제 기능 추가 필요
//            }
//
//            profileImage.addSecondButtonClickListener {
//                getSingleImage()
//            }
//
//            flexibleCost.addDefaultButtonClickListener {
//                startActivity(
//                    EditProfileContainerActivityHelper.getIntent(
//                        requireContext(),
//                        EDIT_FLEXIBLE_COST
//                    )
//                )
//            }
//
//            otherFlexibleOption.addDefaultButtonClickListener {
//                startActivity(
//                    EditProfileContainerActivityHelper.getIntent(
//                        requireContext(),
//                        EDIT_OTHER_FLEXIBLE_OPTION
//                    )
//                )
//            }
//
//            email.addDefaultButtonClickListener {
//                startActivity(
//                    EditProfileContainerActivityHelper.getIntent(
//                        requireContext(),
//                        EDIT_EMAIL
//                    )
//                )
//            }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                DISMISS_LOADING -> dismissLoading()
            }
        })
        viewModel.profile.observe(viewLifecycleOwner, { profile ->
            naverMapHelper.setLocation(
                profile?.requiredInformation?.coordinate,
                profile?.requiredInformation?.serviceArea
            )
        })
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestProfile()
        naverMapHelper
    }

    private fun startActivityCommonCode(pageName: String, itemId: Int? = null) {
        startActivity(
            EditProfileContainerActivityHelper.getIntent(
                requireContext(),
                pageName,
                itemId
            )
        )
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

                        if (viewModel.profile.value?.approvedStatus == CodeTable.APPROVED.code) {
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