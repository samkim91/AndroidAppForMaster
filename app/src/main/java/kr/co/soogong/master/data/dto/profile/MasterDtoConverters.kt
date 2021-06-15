package kr.co.soogong.master.data.dto.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.model.major.Major
import java.util.*

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
    fun projectsToString(list: List<Major>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToProjects(projectsString: String): List<Major> {
        return Gson().fromJson(projectsString, object : TypeToken<List<Major>>() {}.type)
    }
}