package kr.co.soogong.master.atomic.atoms

interface ITextInput {
    var error: String?
    var hint: String?
    var helper: String?
    var enabled: Boolean?
    var maxCount: Int?
    var max: Int?
    var inputType: Int?
}