package kr.co.soogong.master.presentation.ui.common

import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityKakaoAddressBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.uihelper.common.KakaoAddressActivityHelper
import timber.log.Timber

class KakaoAddressActivity : BaseActivity<ActivityKakaoAddressBinding>(
    R.layout.activity_kakao_address
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")

        initLayout()
    }

    override fun initLayout() {
        bind {
            webViewAddress.settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }
            webViewAddress.apply {
                removeJavascriptInterface("masterApp")
                addJavascriptInterface(WebViewData(), "masterApp")
                webChromeClient = WebChromeClient()
                loadUrl("https://s3.ap-northeast-2.amazonaws.com/daum.address/daum_address.html")
            }
        }
    }

    inner class WebViewData {
        @JavascriptInterface
        @SuppressWarnings("unused")
        fun setAddress(area: String?, location: String?, address: String?) {
            CoroutineScope(Dispatchers.Default).launch {
                withContext(Dispatchers.Main) {
                    val extra = Bundle()
                    val intent = Intent()
                    extra.putString(KakaoAddressActivityHelper.AREA, area)
                    extra.putString(KakaoAddressActivityHelper.LOCATION, location)
                    extra.putString(KakaoAddressActivityHelper.ADDRESS, address)
                    intent.putExtras(extra)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    companion object {
        private const val TAG = "KakaoAddressActivity"
    }


}