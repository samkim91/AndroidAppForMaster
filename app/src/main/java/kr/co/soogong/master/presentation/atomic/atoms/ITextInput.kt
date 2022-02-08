package kr.co.soogong.master.presentation.atomic.atoms

interface ITextInput {
    var error: String?
    var hint: String?
    var helper: String?
    var inputEnabled: Boolean?
    var maxCount: Int?
    var max: Int?
    var inputType: Int?
}