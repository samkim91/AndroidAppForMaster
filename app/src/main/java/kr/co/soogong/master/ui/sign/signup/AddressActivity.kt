package kr.co.soogong.master.ui.sign.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAddressBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.sign.signup.AddressActivityHelper
import timber.log.Timber


class AddressActivity : BaseActivity<ActivityAddressBinding>(
    R.layout.activity_address
) {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")

        handler = Handler()
        initLayout()
    }

    fun initLayout() {
        bind {
            webViewAddress.settings.javaScriptEnabled = true
            webViewAddress.settings.javaScriptCanOpenWindowsAutomatically = true
            webViewAddress.addJavascriptInterface(AndroidBridge(), "masterApp")
            webViewAddress.webChromeClient = WebChromeClient()
            webViewAddress.loadUrl("http://daum.address.s3-website.ap-northeast-2.amazonaws.com/")
        }
    }

    inner class AndroidBridge {
        @SuppressWarnings("unused")
        @JavascriptInterface
        fun setAddress(area: String?, location: String?, address: String?) {
            handler.post {
                val extra = Bundle()
                val intent = Intent()
                extra.putString(AddressActivityHelper.AREA, area)
                extra.putString(AddressActivityHelper.LOCATION, location)
                extra.putString(AddressActivityHelper.ADDRESS, address)
                intent.putExtras(extra)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "AddressActivity"
    }
}