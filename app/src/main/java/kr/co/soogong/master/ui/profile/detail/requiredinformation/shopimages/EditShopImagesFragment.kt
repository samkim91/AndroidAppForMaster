package kr.co.soogong.master.ui.profile.detail.requiredinformation.shopimages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.FragmentEditShopImagesBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.image.RectangleImageWithCloseAdapter
import kr.co.soogong.master.ui.profile.detail.requiredinformation.shopimages.EditShopImagesViewModel.Companion.GET_SHOP_IMAGES_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.shopimages.EditShopImagesViewModel.Companion.SAVE_SHOP_IMAGES_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.shopimages.EditShopImagesViewModel.Companion.SAVE_SHOP_IMAGES_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
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

            getImagesButton.setOnClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                        val availableImagesCount =
                            10 - viewModel.shopImages.getItemCount()

                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .max(
                                availableImagesCount,
                                resources.getString(R.string.maximum_images_count)
                            )
                            .startMultiImage { uriList ->
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
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }

            imageList.adapter = RectangleImageWithCloseAdapter(
                closeClickListener = { position ->
                    viewModel.shopImages.removeAt(position)
                }
            )

            defaultButton.setOnClickListener {
                viewModel.saveShopImages()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_SHOP_IMAGES_SUCCESSFULLY -> activity?.onBackPressed()
                SAVE_SHOP_IMAGES_FAILED, GET_SHOP_IMAGES_FAILED -> requireContext().toast(
                    getString(
                        R.string.error_message_of_request_failed
                    )
                )
            }
        })
        viewModel.shopImages.observe(viewLifecycleOwner, {
            Timber.tag(TAG).d("onChanged: $it")
        })
    }

    companion object {
        private const val TAG = "EditShopImagesFragment"

        fun newInstance() = EditShopImagesFragment()
    }
}