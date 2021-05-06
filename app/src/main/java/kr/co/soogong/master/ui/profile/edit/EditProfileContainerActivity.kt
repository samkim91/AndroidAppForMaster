package kr.co.soogong.master.ui.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditProfileContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.profile.edit.portfolio.EditPortfolioFragment
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper.EDIT_PORTFOLIO
import timber.log.Timber

@AndroidEntryPoint
class EditProfileContainerActivity : BaseActivity<ActivityEditProfileContainerBinding>(
    R.layout.activity_edit_profile_container
) {
    private val pageName: String by lazy {
        EditProfileContainerActivityHelper.getPageName(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            with(actionBar) {
                title.text = pageName
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, when (pageName) {
                ADD_PORTFOLIO -> EditPortfolioFragment.newInstance(ADD_PORTFOLIO, null)
                // todo.. get portfolio number from viewholder clicklistener
                EDIT_PORTFOLIO -> EditPortfolioFragment.newInstance(EDIT_PORTFOLIO, 0)


                else -> Fragment()
            }).commit()
    }

    companion object {
        private const val TAG = "EditProfileDetailActivity"

    }
}