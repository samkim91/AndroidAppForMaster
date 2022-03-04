package kr.co.soogong.master.contract

object HttpContract {
    //region Common
    const val GOOGLE_STORE_URL = "https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5"
    const val LOCAL_URL_MH = "http://192.168.0.14:8080/"       // Myeonghwan's local
    const val LOCAL_URL_JH = "http://192.168.0.32:8080/"     // Jihun's local
    const val LOCAL_URL_JY = "http://192.168.0.40:8080/"     // Jaeyeon's local
    const val DEV_URL = "https://backenddev.soogong.co.kr"
    const val TEST_URL = "https://backendtest.soogong.co.kr"
    const val PROD_URL = "https://backend.soogong.co.kr/"
    //endregion

    //region Auth
    const val IS_MASTER_EXISTENT = "master/is-existent"
    const val SAVE_FCM_TOKEN = "firebase/save-token"
    const val UPDATE_UID_BY_TEL = "master/{tel}"
    const val GET_APP_VERSION = "version/android"
    const val SIGN_UP = "master"
    //endregion

    //region Major
    const val CATEGORIES = "category/list"
    const val PROJECTS = "project/find-all-by-categoryId"
    //endregion

    //region Requirement
    const val REQUIREMENT_TOTAL = "master/home/counts"
    const val GET_REQUIREMENTS = "master/requirement"
    const val GET_REQUIREMENT = "master/find-requirement-one"
    const val SEARCH_REQUIREMENTS = "requirement/search-for-master"
    //endregion

    //region Estimations
    const val SAVE_ESTIMATION = "estimation/save"
    const val RESPOND_TO_MEASURE = "estimation/reply-measure"
    const val CALL_TO_CLIENT = "estimation/count-call"
    const val GET_CUSTOMER_REQUESTS = "estimation/get-contact-list"
    //Estimation Template
    const val GET_ESTIMATION_TEMPLATES = "estimation-template/find-by-master-uid"
    const val SAVE_ESTIMATION_TEMPLATE = "estimation-template"
    const val DELETE_ESTIMATION_TEMPLATE = "estimation-template/{id}"
    //endregion

    //region Repair
    const val SAVE_REPAIR = "repair/save"
    const val REQUEST_REVIEW_V2 = "repair/v2/request-review/{id}"
    //endregion

    //region Cancel
    const val GET_CANCELED_REASONS = "code/find-all-by-groupCodes"
    //endregion

    //region Profile
    const val MY_PAGE_URL = "https://soogong.co.kr/master/detail/"
    const val GET_MASTER_SETTINGS = "master/{uid}/settings"
    const val GET_MASTER = "master/{uid}"
    const val SAVE_MASTER = "master/save"
    const val GET_REVIEWS = "master/review"
    const val UPDATE_REQUEST_MEASURE_YN = "master/request-measure-yn"
    const val UPDATE_FREE_MEASURE_YN = "master/free-measure-yn"
    const val UPDATE_DIRECT_REPAIR_YN = "master/direct-repair-yn"
    const val REQUEST_REVIEW_BY_SHARING = "https://soogong.co.kr/my-soogong/easy-review/"
    //endregion

    //region Portfolio
    const val SAVE_PORTFOLIO = "master/portfolio/save"
    const val GET_PORTFOLIOS = "master/portfolio"
    const val DELETE_PORTFOLIO = "master/portfolio/{id}"
    //endregion

    //region Preferences
    const val GET_NOTICE_LIST = "notice/find-all-by-section"
    const val GET_NOTICE = "notice/find-by-id"
    const val GET_ALARMS = "api/v1/branches/get_alarm"
    const val SET_ALARMS = "api/v1/branches/set_alarm"
    //endregion
}