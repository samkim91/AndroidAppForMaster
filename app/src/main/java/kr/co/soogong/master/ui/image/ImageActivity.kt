package kr.co.soogong.master.ui.image

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.ImagePath
import kr.co.soogong.master.databinding.ActivityImageBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

@AndroidEntryPoint
class ImageActivity : BaseActivity<ActivityImageBinding>(
    R.layout.activity_image
) {
    private val startPosition: Int by lazy {
        ImageViewActivityHelper.getImagePosition(intent)
    }

    private val images: ArrayList<ImagePath> by lazy {
        ImageViewActivityHelper.getImages(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
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
                if(!images.isNullOrEmpty()) {
                    setList(images)
                    setupIndicators(images.size)
                    binding.sliderViewPager.setCurrentItem(startPosition, false)
                }
            }
        }
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