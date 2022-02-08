package kr.co.soogong.master.presentation.atomic.atoms

import android.widget.ArrayAdapter

interface IDropdownMenu {
    var dropdownAdapter: ArrayAdapter<Any>?
    var dropdownError: String?
    var dropdownHint: String?
    var dropdownHelper: String?
    var dropdownInputType: Int?
}