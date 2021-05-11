package kr.co.soogong.master.ui.profile.review

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMyReviewsBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class MyReviewsActivity : BaseActivity<ActivityMyReviewsBinding>(
    R.layout.activity_my_reviews
) {
    private val viewModel: MyReviewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            with(actionBar){
                title.text = getString(R.string.my_reviews_action_bar_label)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }


        }
    }

    companion object {
        private const val TAG = "MyReviewsActivity"
    }
}