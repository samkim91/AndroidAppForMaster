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
    const val REQUEST_CERTIFICATION_CODE = "auth/request"
    const val VERIFY_CERTIFICATION_CODE = "auth/verify"

    const val SAVE_FCM_TOKEN = "firebase/save-token"
    const val GET_APP_VERSION = "version/android"
    const val SIGN_UP = "master"
    const val SIGN_IN = "master/sign-in"
    //endregion

    //region Major
    const val CATEGORIES = "category/v2"
    const val PROJECTS = "project/find-all-by-categoryId-v2"
    //endregion

    //region Requirement
    const val REQUIREMENT_TOTAL = "master/home/counts"
    const val GET_REQUIREMENTS = "master/{uid}/requirements"
    const val GET_REQUIREMENT = "master/requirement/{requirementId}"
    //endregion

    //region Estimations
    const val ACCEPT_TO_MEASURE = "estimation/accept-measure"
    const val REFUSE_TO_MEASURE = "estimation/refuse-measure"
    const val INCREASE_CALL_COUNT = "estimation/count-call"
    const val GET_CUSTOMER_REQUESTS = "estimation/get-contact-list"
    const val SAVE_MASTER_NOTE = "estimation/{token}/master-memo"
    const val UPDATE_VISITING_DATE = "estimation/{token}/visit-date"

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
    const val SAVE_PORTFOLIO = "master/portfolio"
    const val SAVE_REPAIR_PHOTO = "master/photo"
    const val SAVE_PRICE_BY_PROJECT = "master/price"
    const val GET_PORTFOLIOS = "master/{type}"
    const val DELETE_PORTFOLIO = "master/portfolio/{id}"
    //endregion

    //region Preferences
    const val GET_NOTICE_LIST = "notice/find-all-by-section"
    const val GET_NOTICE = "notice/find-by-id"
    const val SET_PUSH_AT_NIGHT = "master/{uid}/push-at-night"
    const val SET_MARKETING_PUSH = "master/{uid}/marketing-push"
    //endregion
}