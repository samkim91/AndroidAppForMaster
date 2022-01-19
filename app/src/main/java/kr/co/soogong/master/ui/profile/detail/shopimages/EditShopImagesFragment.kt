package kr.co.soogong.master.ui.profile.detail.shopimages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.global.ButtonTheme
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.databinding.FragmentEditShopImagesBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.DefaultDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel.Companion.SAVE_MASTER_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditShopImagesFragment : BaseFragment<FragmentEditShopImagesBinding>(
    R.layout.fragment_edit_shop_images
) {
    private val viewModel: EditShopImagesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestShopImages()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            buttonThemeGettingImages = ButtonTheme.OutlinedPrimary

            gettingImagesClickListener = View.OnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        val availableImagesCount = 10 - viewModel.shopImages.getItemCount()

                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_green_background_radius8)
                            .max(
                                availableImagesCount,
                                resources.getString(R.string.maximum_images_count)
                            )
                            .startMultiImage { uriList ->
                                if (FileHelper.isImageExtension(uriList,
                                        requireContext()) == false
                                ) {
                                    requireContext().toast(getString(R.string.invalid_image_extension))
                                    return@startMultiImage
                                }

                                viewModel.shopImages.addAll(uriList.map {
                                    AttachmentDto(
                                        id = null,
                                        partOf = null,
                                        referenceId = null,
                                        description = null,
                                        s3Name = null,
                                        fileName = null,
                                        url = null,
                                        uri = it,
                                    )
                                })
                            }
                    },
                    onDenied = { })
            }

            sidRegisteredImages.setImagesDeletableAdapter { position ->
                viewModel.shopImages.removeAt(position)
            }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                if (viewModel.profile.value?.approvedStatus == CodeTable.APPROVED.code) {
                    DefaultDialog.newInstance(
                        DialogData.getConfirmingForLimitedService())
                        .let {
                            it.setButtonsClickListener(
                                onPositive = {
                                    showLoading(parentFragmentManager)
                                    viewModel.saveShopImages()
                                },
                                onNegative = { }
                            )
                            it.show(parentFragmentManager, it.tag)
                        }
                } else {
                    showLoading(parentFragmentManager)
                    viewModel.saveShopImages()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_MASTER_SUCCESSFULLY -> {
                    dismissLoading()
                    activity?.onBackPressed()
                }
                REQUEST_FAILED -> {
                    dismissLoading()
                    requireContext().toast(getString(
                        R.string.error_message_of_request_failed
                    ))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditShopImagesFragment"

        fun newInstance() = EditShopImagesFragment()
    }
}