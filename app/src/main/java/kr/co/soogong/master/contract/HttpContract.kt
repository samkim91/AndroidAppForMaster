package kr.co.soogong.master.contract

object HttpContract {
    //    const val LOCAL_URL = "http://192.168.0.50:8080/"       // Myeonghwan's local
    const val LOCAL_URL_JH = "http://192.168.0.32:8080/"     // Jihun's local
    const val LOCAL_URL = "http://192.168.0.40:8080/"     // Jaeyeon's local
    const val DEV_URL = "https://backenddev.soogong.co.kr"
    const val TEST_URL = "https://backendtest.soogong.co.kr"
    const val PROD_URL = "https://backend.soogong.co.kr/"

    //region Auth
    const val IS_MASTER_EXISTENT = "master/is-existent"
    const val SAVE_FCM_TOKEN = "firebase/save-token"
    const val UPDATE_UID_BY_TEL = "master/{tel}"
    const val GET_APP_VERSION = "version/android"
    //endregion

    //region Category
    const val CATEGORY_LIST = "category/list"
    const val PROJECT_LIST = "project/find-all-by-categoryId"
    //endregion

    //region Requirement
    const val GET_REQUIREMENT_LIST_BY_UID = "master/find-all-requirement-by-uid"
    const val GET_REQUIREMENT = "master/find-requirement-one"
    const val SEARCH_REQUIREMENTS = "requirement/search-for-master"
    //endregion

    //region Estimations
    const val SAVE_ESTIMATION = "estimation/save"
    const val RESPOND_TO_MEASURE = "estimation/reply-measure"
    const val CALL_TO_CLIENT = "estimation/count-call"
    const val GET_CUSTOMER_REQUESTS = "estimation/get-contact-list"
    //endregion

    //region Repair
    const val SAVE_REPAIR = "repair/save"
    const val REQUEST_REVIEW = "repair/request-review"
    //endregion

    //region Cancel
    const val GET_CANCELED_REASONS = "code/find-all-by-groupCodes"
    //endregion

    //region Estimation Template
    const val GET_ESTIMATION_TEMPLATES = "estimation-template/find-by-master-uid"
    const val SAVE_ESTIMATION_TEMPLATE = "estimation-template"
    const val DELETE_ESTIMATION_TEMPLATE = "estimation-template/{id}"
    //endregion

    //region Profile
    const val MY_PAGE_URL = "https://soogong.co.kr/master/detail/"
    const val GET_MASTER_SIMPLE_INFO = "master/find-simple-info"
    const val GET_MASTER_BY_UID = "master/find-by-uid"
    const val SAVE_MASTER = "master/save"
    const val UPDATE_REQUEST_MEASURE_YN = "master/request-measure-yn"
    const val UPDATE_FREE_MEASURE_YN = "master/free-measure-yn"
    const val UPDATE_DIRECT_REPAIR_YN = "master/direct-repair-yn"
    const val REQUEST_REVIEW_BY_SHARING = "https://soogong.co.kr/my-soogong/easy-review/"
    //endregion

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