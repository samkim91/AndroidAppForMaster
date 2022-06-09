package kr.co.soogong.master.presentation.uihelper.requirment.action

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kr.co.soogong.master.presentation.ui.requirement.action.visit.template.EstimationTemplatesActivity

object EstimationTemplatesActivityHelper {
    private const val BUNDLE_KEY = "BUNDLE_KEY"
    private const val ESTIMATION_TEMPLATE = "ESTIMATION_TEMPLATE"

    fun getIntent(context: Context) = Intent(context, EstimationTemplatesActivity::class.java)

    fun setResponse(template: String) = Intent().apply {
        putExtra(BUNDLE_KEY, Bundle().apply {
            putString(ESTIMATION_TEMPLATE, template)
        })
    }

    fun getResponse(intent: Intent) =
        intent.getBundleExtra(BUNDLE_KEY)?.getString(ESTIMATION_TEMPLATE)
}