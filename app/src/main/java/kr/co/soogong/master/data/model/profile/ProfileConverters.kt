package kr.co.soogong.master.data.model.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.model.major.Major

class ProfileConverters {
//    @TypeConverter
//    fun businessTypeListToString(majorList: List<Major>?): String? {
//        return Gson().toJson(majorList)
//    }
//
//    @TypeConverter
//    fun stringToBusinessTypeList(businessTypeListString: String?): List<Major>? {
//        return Gson().fromJson(businessTypeListString, object : TypeToken<List<Major>>() {}.type)
//    }

    @TypeConverter
    fun basicInformationToString(basicInformation: BasicInformation): String {
        return Gson().toJson(basicInformation)
    }

    @TypeConverter
    fun stringToBasicInformation(basicInformationString: String): BasicInformation {
        return Gson().fromJson(basicInformationString, object : TypeToken<BasicInformation>() {}.type)
    }

    @TypeConverter
    fun myReviewToString(myReview: MyReview): String {
        return Gson().toJson(myReview)
    }

    @TypeConverter
    fun stringToMyReview(myReviewString: String): MyReview {
        return Gson().fromJson(myReviewString, object : TypeToken<MyReview>() {}.type)
    }

//    @TypeConverter
//    fun requiredInformationToString(requiredInformation: RequiredInformation): String {
//        return Gson().toJson(requiredInformation)
//    }
//
//    @TypeConverter
//    fun stringToRequiredInformation(requiredInformationString: String): RequiredInformation {
//        return Gson().fromJson(requiredInformationString, object : TypeToken<RequiredInformation>() {}.type)
//    }
}