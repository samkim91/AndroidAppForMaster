package kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditRepresentativeImagesBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.image.RectangleImageWithCloseAdapter
import kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages.EditRepresentativeImagesViewModel.Companion.SAVE_REPRESENTATIVE_IMAGES_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages.EditRepresentativeImagesViewModel.Companion.SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY
import kr.co.soogong.master.ui.utils.PermissionHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditRepresentativeImagesFragment : BaseFragment<FragmentEditRepresentativeImagesBinding>(
    R.layout.fragment_edit_representative_images
) {
    private val viewModel: EditRepresentativeImagesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.getRepresentativeImg()
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
                            10 - viewModel.representativeImages.getItemCount()

                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .max(
                                availableImagesCount,
                                resources.getString(R.string.maximum_images_count)
                            )
                            .startMultiImage { uriList ->
                                viewModel.representativeImages.addAll(uriList)
                                imageList.adapter?.notifyDataSetChanged()
                            }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }

            imageList.adapter = RectangleImageWithCloseAdapter(
                closeClickListener = { position ->
                    viewModel.representativeImages.removeAt(position)
                    imageList.adapter?.notifyDataSetChanged()
                }
            )

            defaultButton.setOnClickListener {
                viewModel.saveRepresentativeImg()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when(event) {
                SAVE_REPRESENTATIVE_IMAGES_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_REPRESENTATIVE_IMAGES_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditRepresentativeImagesFragment"

        fun newInstance() = EditRepresentativeImagesFragment()
    }
}