package kr.co.soogong.master.contract

object HttpContract {
    const val MY_PAGE_URL = "https://soogong.co.kr/master/"

    const val LOCAL_URL = "http://192.168.0.50:8080/"
    const val TEST_URL = "https://test.api2.soogong.co.kr/"
    const val PROD_URL = "https://api2.soogong.co.kr/"

    //region Auth
    const val MASTER_SIGN_UP = "auth/master-sign-up"
    const val MASTER_SIGN_IN = "auth/master-find-by-uid"
    const val IS_USER_EXIST = "auth/user-exist"
    const val FCM_UPDATE = "api/v1/branches/update_reg_id"  // TODO: 2021/06/10 무슨 용도인지 알아보고 추가해야함.
    //endregion

    //region Category
    const val CATEGORY_LIST = "category/list"
    const val PROJECT_LIST = "project/find-all-by-categoryId/{categoryId}"
    //endregion

    //region User
    const val GET_MASTER_BY_UID = "master/find-by-uid"
    //endregion

    //region Notice
    const val GET_NOTICE = "api/v1/boards/notice"
    //endregion

    //region Alarm
    const val GET_ALARMS = "api/v1/branches/get_alarm"
    const val SET_ALARMS = "api/v1/branches/set_alarm"
    //endregion

    //region Estimations
    const val GET_ESTIMATION_V2 = "api/v2/estimations/{branch_keycode}"
    const val GET_ESTIMATION = "api/v1/transmissions/default_list"
    const val ACCEPT_ESTIMATION = "api/v1/transmissions/accept_list"
    const val REFUSE_ESTIMATION = "api/v1/transmissions/refuse"
//    const val SEND_ESTIMATION_MESSAGE = "api/v1/transmissions/send_message"
    //endregion

    //region Master
    const val MASTER_MY_PAGE = "https://soogong.co.kr/master/detail/"

    // TODO.. 아래 HTTP request의 경우, 카카오톡 웹뷰에서 바로 request가 나가서, API server를 거치지 않고 있음. 수정이 되면 다시 바꿔줘야함.
    const val SEND_ESTIMATION_MESSAGE = "https://partner.soogong.co.kr/estimations/sc"
    const val CANCEL_ESTIMATE = "https://partner.soogong.co.kr/estimations/sc/refuse"
    const val END_ESTIMATE = "https://partner.soogong.co.kr/estimations/execute"
    const val CALL_TO_CUSTOMER = "https://partner.soogong.co.kr/estimations/calltocustomer"
    const val ASK_FOR_REVIEW = "https://partner.soogong.co.kr/estimations/askforreview"
    //endregion
}