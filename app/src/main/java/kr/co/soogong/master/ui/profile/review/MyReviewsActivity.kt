package kr.co.soogong.master.ui.profile.review

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMyReviewsBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.profile.review.MyReviewsViewModel.Companion.GET_MY_REVIEWS_FAILED
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class MyReviewsActivity : BaseActivity<ActivityMyReviewsBinding>(
    R.layout.activity_my_reviews
) {
    private val viewModel: MyReviewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@MyReviewsActivity

            abHeader.setButtonBackClickListener { onBackPressed() }

            rvReviews.adapter = ReviewAdapter(this@MyReviewsActivity)
        }
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                GET_MY_REVIEWS_FAILED -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestMyReviews()
    }

    companion object {
        private const val TAG = "MyReviewsActivity"
    }
}