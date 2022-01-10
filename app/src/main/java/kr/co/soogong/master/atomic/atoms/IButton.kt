package kr.co.soogong.master.atomic.atoms

import android.view.View
import kr.co.soogong.master.data.global.ButtonTheme

interface IButton {
    var buttonText: String?
    var buttonEnable: Boolean?
    var buttonTheme: ButtonTheme?
    var onButtonClick: View.OnClickListener?
}