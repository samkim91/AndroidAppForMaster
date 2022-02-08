package kr.co.soogong.master.presentation.ui.common.image

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityImageBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.uihelper.common.image.ImageViewActivityHelper
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

@AndroidEntryPoint
class ImageActivity : BaseActivity<ActivityImageBinding>(
    R.layout.activity_image
) {
    private val images: List<String> by lazy {
        ImageViewActivityHelper.getImages(intent)
    }

    private val startPosition: Int by lazy {
        ImageViewActivityHelper.getImagePosition(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: $startPosition")
        bind {
            lifecycleOwner = this@ImageActivity

            closeButton.setOnClickListener {
                super.onBackPressed()
            }

            with(sliderViewPager) {
                offscreenPageLimit = 1
                adapter = ImagePagerAdapter(this@ImageActivity, images = images)
                registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Timber.tag(TAG).i("onPageSelected: $position")
                        setCurrentIndicator(position)
                    }
                })
            }

            initIndicators(images.count())
            sliderViewPager.currentItem = startPosition
        }
    }

    // 인디케이터 init
    private fun initIndicators(count: Int) {
        Timber.tag(TAG).d("initIndicators: $count")
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

    // 인디케이터에 변화주는 코드. 선택된 것을 활성화, 선택되지 않은 것들을 비활성화한다.
    private fun setCurrentIndicator(position: Int) {
        Timber.tag(TAG).d("setCurrentIndicator: $position")
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