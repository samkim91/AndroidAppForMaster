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
import kr.co.soogong.master.data.global.ButtonTheme
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.data.global.ColorTheme
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.DISMISS_LOADING
import kr.co.soogong.master.ui.base.BaseViewModel.Companion.SHOW_LOADING
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogBundle
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogRecyclerView
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.ProfileViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.*
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_ADDRESS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_BUSINESS_UNIT_INFORMATION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_EMAIL
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_INTRODUCTION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_MAJOR
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_MASTER_CONFIG
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PHONE_NUMBER
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_SHOP_IMAGES
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_WARRANTY_INFORMATION
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.FREE_MEASURE
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.NaverMapHelper
import kr.co.soogong.master.utility.PermissionHelper
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
                startActivity(MyReviewsActivityHelper.getIntent(requireContext()))
            }

            abHeader.setButtonAnyClickListener {
                startActivity(ShowMyProfileInWebHelper.getIntent(viewModel.profile.value?.uid))
            }

            // TODO: 2021/12/15 Profile image 삭제 기능 필요
            ivProfileImage.setOnClickListener {
                getSingleImage()
            }

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

            hbcOwnerName.tvContent.isSingleLine = true      // 기본 3줄이 max 인데, 여기는 한줄로 제한
            hbcOwnerName.onButtonClick = View.OnClickListener {
                with(hbcOwnerName.tvContent) {
                    if (this.isEnabled) {                 // 수정상태이면, 저장하기 버튼 활성화 및 클릭 이벤트 실행
                        this.isEnabled = false
                        hbcOwnerName.setButtonStatus()
                        viewModel.saveOwnerName()
                    } else {                              // 수정상태가 아니라면, 수정 가능하게 만들고 포커스와 키보드 보이게 처리
                        this.isVisible = true
                        this.isEnabled = true
                        this.requestFocus()
                        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                            .showSoftInput(this, 0)
                        hbcOwnerName.setButtonStatus()
                        this.setSelection(this.length())
                    }
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
                            DefaultDialog.newInstance(DialogData.getConfirmingForLimitedService())
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

            hbcMasterConfigs.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_MASTER_CONFIG) }

            hbcPortfolio.onButtonClick = View.OnClickListener {
                startActivity(PortfolioListActivityHelper.getIntent(requireContext(),
                    CodeTable.PORTFOLIO))
            }

            hbcPriceByProject.onButtonClick = View.OnClickListener {
                startActivity(PortfolioListActivityHelper.getIntent(requireContext(),
                    CodeTable.PRICE_BY_PROJECT))
            }

            hbcEmailAddress.onButtonClick =
                View.OnClickListener { startActivityCommonCode(EDIT_EMAIL) }
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                REQUEST_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
                SHOW_LOADING -> showLoading(parentFragmentManager)
                DISMISS_LOADING -> dismissLoading()
            }
        })
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            naverMapHelper.setLocation(
                profile?.requiredInformation?.coordinate,
                profile?.requiredInformation?.serviceArea
            )
            setRequiredProfileInformationProgress(viewModel).run {
                checkApprovedStatusAndRequiredField(parentFragmentManager, binding, viewModel, this)
            }
            setOptionalProfileInformationProgress(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        naverMapHelper
        viewModel.requestProfile()
    }

    private fun startActivityCommonCode(pageName: String) {
        startActivity(
            EditProfileContainerActivityHelper.getIntent(requireContext(), pageName)
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
                            DefaultDialog.newInstance(
                                DialogData.getConfirmingForLimitedService())
                                .let {
                                    it.setButtonsClickListener(
                                        onPositive = {
                                            viewModel.saveMasterProfileImage()
                                        },
                                        onNegative = { }
                                    )
                                    it.show(parentFragmentManager, it.tag)
                                }
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
        fun newInstance() = ProfileFragment()
    }
}