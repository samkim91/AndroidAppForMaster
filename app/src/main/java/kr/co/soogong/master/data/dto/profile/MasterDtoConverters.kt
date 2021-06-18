package kr.co.soogong.master.data.dto.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MasterDtoConverters {
    @TypeConverter
    fun masterConfigToString(list: List<MasterConfigDto>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToMasterConfig(masterConfigString: String): List<MasterConfigDto> {
        return Gson().fromJson(
            masterConfigString,
            object : TypeToken<List<MasterConfigDto>>() {}.type
        )
    }

    @TypeConverter
    fun masterPortfolioToString(list: List<PortfolioDto>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToMasterPortfolio(masterPortfolioString: String): List<PortfolioDto> {
        return Gson().fromJson(
            masterPortfolioString,
            object : TypeToken<List<PortfolioDto>>() {}.type
        )
    }

    @TypeConverter
    fun projectsToString(list: List<MajorDto>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToProjects(projectsString: String): List<MajorDto> {
        return Gson().fromJson(projectsString, object : TypeToken<List<MajorDto>>() {}.type)
    }
}