package kr.co.soogong.master.presentation.uihelper.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity

object EditProfileContainerActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY"
    private const val EXTRA_PORTFOLIO = "EXTRA_PORTFOLIO"

    // [Note] Edit Profile Container Activity 는 1depth 로 이동하는 경우와 2depth 로 이동하는 경우가 있음
    // 2depth 는 포트폴리오, 시공 사진, 시공 종류별 가격 수정하는 경우
    // 1depth 는 위를 제외한 나머지 수정하는 경우
    fun getIntent(context: Context, pageName: String): Intent {
        return Intent(context, EditProfileContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
            })
        }
    }

    fun getIntentForEditingPortfolio(
        context: Context,
        pageName: String,
        portfolioDto: PortfolioDto? = null,
    ): Intent =
        Intent(context, EditProfileContainerActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, bundleOf(
                EXTRA_STRING_KEY to pageName,
                EXTRA_PORTFOLIO to portfolioDto
            ))
        }

    fun getPageName(intent: Intent): String =
        intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY) ?: ""

    fun getPortfolio(intent: Intent): PortfolioDto? =
        intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getParcelable(EXTRA_PORTFOLIO)
}