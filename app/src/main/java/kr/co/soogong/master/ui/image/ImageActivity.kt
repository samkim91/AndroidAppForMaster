package kr.co.soogong.master.ui.image

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityImageBinding
import kr.co.soogong.master.ext.dp
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.image.ImageViewActivityHelper
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ImageActivity : BaseActivity<ActivityImageBinding>(
    R.layout.activity_image
) {
    private val estimationId: String by lazy {
        ImageViewActivityHelper.getEstimationId(intent)
    }

    private val startPosition: Int by lazy {
        ImageViewActivityHelper.getImagePosition(intent)
    }

    @Inject
    lateinit var factory: ImageViewModel.AssistedFactory

    private val viewModel: ImageViewModel by viewModels {
        ImageViewModel.provideFactory(factory, estimationId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@ImageActivity

            closeButton.setOnClickListener {
                super.onBackPressed()
            }

            with(sliderViewPager) {
                offscreenPageLimit = 1
                adapter = ImageSliderAdapter()
                currentItem = startPosition
                registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrentIndicator(position)
                    }
                })
            }
        }

        viewModel.imagePath.observe(this, {
            if (it.isEmpty()) return@observe
            setupIndicators(it.size)
            binding.sliderViewPager.setCurrentItem(startPosition, false)
        })
    }

    private fun setupIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(2.dp, 8.dp, 2.dp, 8.dp)

        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_indicator_inactive
                )
            )
            indicators[i]?.layoutParams = params
            binding.layoutIndicators.addView(indicators[i])
        }
    }

    fun setCurrentIndicator(position: Int) {
        val childCount: Int = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                    )
                )
            }
        }
    }

    companion object {
        private const val TAG = "ImageActivity"
    }
}