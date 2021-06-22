package kr.co.soogong.master.uihelper.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.ui.profile.edit.portfoliolist.PortfolioListActivity

object PortfolioListActivityHelper {
    const val PORTFOLIO = "포트폴리오 편집하기"
    const val PRICE_BY_PROJECTS = "시공 종류별 가격 편집하기"

    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY"
    private const val EXTRA_STRING_KEY = "EXTRA_STRING_KEY"

    fun getIntent(context: Context, pageName: String): Intent {
        return Intent(context, PortfolioListActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, Bundle().apply {
                putString(EXTRA_STRING_KEY, pageName)
            })
        }
    }

    fun getPageName(intent: Intent): String {
        return intent.getBundleExtra(EXTRA_BUNDLE_KEY)?.getString(EXTRA_STRING_KEY, "") ?: ""
    }
}