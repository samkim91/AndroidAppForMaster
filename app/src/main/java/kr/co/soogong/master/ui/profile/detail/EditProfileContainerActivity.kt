package kr.co.soogong.master.ui.profile.detail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.ButtonTheme
import kr.co.soogong.master.databinding.ActivityEditProfileContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import timber.log.Timber

@AndroidEntryPoint
class EditProfileContainerActivity : BaseActivity<ActivityEditProfileContainerBinding>(
    R.layout.activity_edit_profile_container
) {
    private val pageName: String by lazy {
        EditProfileContainerActivityHelper.getPageName(intent)
    }

    private val portfolioDto: PortfolioDto? by lazy {
        EditProfileContainerActivityHelper.getPortfolio(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: $pageName")

        bind {
            buttonThemeSave = ButtonTheme.Primary

            with(abHeader) {
                title = pageName
                setButtonBackClickListener { super.onBackPressed() }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fcv_container,
                if (pageName == ADD_PORTFOLIO || pageName == EDIT_PORTFOLIO || pageName == ADD_PRICE_BY_PROJECTS || pageName == EDIT_PRICE_BY_PROJECTS)
                    EditProfileContainerFragmentHelper.getFragmentWithPortfolio(pageName,
                        portfolioDto)
                else EditProfileContainerFragmentHelper.getFragment(pageName)
            ).commit()
    }

    fun setSaveButtonClickListener(onClick: () -> Unit) {
        binding.bfSave.onButtonClick = View.OnClickListener {
            onClick()
        }
    }

    companion object {
        private const val TAG = "EditProfileDetailActivity"

    }
}