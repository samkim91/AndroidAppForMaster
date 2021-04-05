package kr.co.soogong.master.contract

object HttpContract {
    const val TEST_URL = "https://test.api2.soogong.co.kr/"
    const val PROD_URL = "https://api2.soogong.co.kr/"

    //region Auth
    const val LOGIN = "login"
    const val FIND = "find"
    const val SIGN_UP = "signup"
    const val MASTER_SIGN_UP = "api/v1/branches/register"
    const val PASSWORD = "password"
    const val FCM_UPDATE = "api/v1/branches/update_reg_id"
    //endregion

    //region Category
    const val CATEGORY = "api/v1/categories"
    const val PROJECT = "api/v1/categories/{id}/projects"
    //endregion

    //region User
    const val GET_USER_PROFILE = "api/v1/branches/search/{keycode}"
    const val GET_USER_PROFILE_V2 = "api/v2/branches/info/{keycode}"
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
    const val SEND_ESTIMATION_MESSAGE = "api/v1/transmissions/send_message"
    //endregion

    //region
    const val MASTER_MY_PAGE = "https://soogong.co.kr/master/detail/"
    //endregion
}