package kr.co.soogong.master.presentation.ui.common.image

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoViewAttacher
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentImageBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import timber.log.Timber

class ImageFragment : BaseFragment<FragmentImageBinding>(
    R.layout.fragment_image
) {

    private val imageUrl: String? by lazy {
        arguments?.getString(IMAGE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        imageUrl?.run {
            Glide.with(requireContext())
                .load(this)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .into(binding.pvImage)

            PhotoViewAttacher(binding.pvImage)
        }
    }

    companion object {
        private const val TAG = "ImageFragment"
        private const val IMAGE = "IMAGE"

        fun newInstance(imageUrl: String) = ImageFragment().apply {
            arguments = bundleOf(
                IMAGE to imageUrl
            )
        }
    }
}