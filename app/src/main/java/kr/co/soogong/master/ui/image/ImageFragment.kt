package kr.co.soogong.master.ui.image

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoViewAttacher
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.databinding.FragmentImageBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

class ImageFragment : BaseFragment<FragmentImageBinding>(
    R.layout.fragment_image
) {

    private val image: AttachmentDto? by lazy {
        arguments?.getParcelable(IMAGE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        image?.run {
            Glide.with(requireContext())
                .load(this.url)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .into(binding.pvImage)

            PhotoViewAttacher(binding.pvImage)
        }
    }

    companion object {
        private const val TAG = "ImageFragment"
        private const val IMAGE = "IMAGE"

        fun newInstance(attachmentDto: AttachmentDto) = ImageFragment().apply {
            arguments = bundleOf(
                IMAGE to attachmentDto
            )
        }
    }
}