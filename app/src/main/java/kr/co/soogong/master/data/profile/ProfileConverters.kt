package kr.co.soogong.master.data.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.estimation.AdditionalInfo
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.data.estimation.Transmissions
import java.util.*
import kotlin.collections.ArrayList

class ProfileConverters {
    @TypeConverter
    fun businessTypeListToString(businessTypeList: List<BusinessType>?): String? {
        return Gson().toJson(businessTypeList)
    }

    @TypeConverter
    fun stringToBusinessTypeList(businessTypeListString: String?): List<BusinessType>? {
        return Gson().fromJson(businessTypeListString, object : TypeToken<List<BusinessType>>() {}.type)
    }

    @TypeConverter
    fun basicInformationToString(basicInformation: BasicInformation): String {
        return Gson().toJson(basicInformation)
    }

    @TypeConverter
    fun stringToBasicInformation(basicInformationString: String): BasicInformation {
        return Gson().fromJson(basicInformationString, object : TypeToken<BasicInformation>() {}.type)
    }

    @TypeConverter
    fun requiredInformationToString(requiredInformation: RequiredInformation): String {
        return Gson().toJson(requiredInformation)
    }

    @TypeConverter
    fun stringToRequiredInformation(requiredInformationString: String): RequiredInformation {
        return Gson().fromJson(requiredInformationString, object : TypeToken<RequiredInformation>() {}.type)
    }
}