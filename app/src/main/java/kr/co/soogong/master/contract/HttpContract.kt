package kr.co.soogong.master.contract

object HttpContract {
//    const val LOCAL_URL = "http://192.168.0.50:8080/"       // Myeonghwan's local
    const val LOCAL_URL = "http://192.168.0.93:8080/"     // Jihun's local
    const val DEV_URL = "https://backenddev.soogong.co.kr"
    const val PROD_URL = "https://backendmain.soogong.co.kr/"

    //region Auth
    const val IS_USER_EXIST = "auth/user-exist"
    const val IS_MASTER_EXISTENT = "master/is-existent"
    const val SAVE_FCM_TOKEN = "firebase/save-token"
    //endregion

    //region Category
    const val CATEGORY_LIST = "category/list"
    const val PROJECT_LIST = "project/find-all-by-categoryId"
    //endregion

    //region Requirement
    const val GET_REQUIREMENT_LIST = "master/find-all-requirement-by-id"
    const val GET_REQUIREMENT = "master/find-requirement-by-id"
    //endregion

    //region Estimations
    const val SAVE_ESTIMATION = "estimation/save"
    const val CALL_TO_CLIENT = "estimation/count-call"
    //endregion

    //region Repair
    const val SAVE_REPAIR = "repair/save"
    const val REQUEST_REVIEW = "repair/request-review"
    //endregion

    //region Profile
    const val MY_PAGE_URL = "https://soogong.co.kr/master/"
    const val GET_MASTER_BY_UID = "master/find-by-uid"
    const val SAVE_MASTER = "master/save"

    //region Portfolio
    const val SAVE_PORTFOLIO = "masterPortfolio/save"
    const val GET_PORTFOLIOS = "masterPortfolio/find-all-by-masterUid"
    const val DELETE_PORTFOLIO = "masterPortfolio/delete/{id}"
    //endregion

    //region MyPage
    const val GET_NOTICE_LIST = "board/find-all-by-type"
    const val GET_NOTICE = "board/find-by-id"
    const val GET_ALARMS = "api/v1/branches/get_alarm"
    const val SET_ALARMS = "api/v1/branches/set_alarm"
    //endregion

}