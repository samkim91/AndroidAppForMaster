package kr.co.soogong.master.ui.profile.edit.requiredinformation.thumbnails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditThumbnailsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.image.RectangleImageWithCloseAdapter
import kr.co.soogong.master.ui.profile.edit.requiredinformation.thumbnails.EditThumbnailsViewModel.Companion.GET_THUMBNAILS_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.thumbnails.EditThumbnailsViewModel.Companion.SAVE_THUMBNAILS_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.thumbnails.EditThumbnailsViewModel.Companion.SAVE_THUMBNAILS_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditThumbnailsFragment : BaseFragment<FragmentEditThumbnailsBinding>(
    R.layout.fragment_edit_thumbnails
) {
    private val viewModel: EditThumbnailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestThumbnails()
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
                            10 - viewModel.thumbnails.getItemCount()

                        TedImagePicker.with(requireContext())
                            .buttonBackground(R.drawable.shape_fill_green_background)
                            .max(
                                availableImagesCount,
                                resources.getString(R.string.maximum_images_count)
                            )
                            .startMultiImage { uriList ->
                                viewModel.thumbnails.addAll(uriList)
                                imageList.adapter?.notifyDataSetChanged()
                            }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    })
            }

            imageList.adapter = RectangleImageWithCloseAdapter(
                closeClickListener = { position ->
                    viewModel.thumbnails.removeAt(position)
                    imageList.adapter?.notifyDataSetChanged()
                }
            )

            defaultButton.setOnClickListener {
                viewModel.saveThumbnails()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_THUMBNAILS_SUCCESSFULLY -> activity?.onBackPressed()
                SAVE_THUMBNAILS_FAILED, GET_THUMBNAILS_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditThumbnailsFragment"

        fun newInstance() = EditThumbnailsFragment()
    }
}