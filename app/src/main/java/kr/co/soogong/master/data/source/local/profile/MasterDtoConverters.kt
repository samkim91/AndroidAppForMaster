package kr.co.soogong.master.data.source.local.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.entity.common.major.ProjectDto
import kr.co.soogong.master.data.entity.profile.MasterConfigDto
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto

class MasterDtoConverters {
    @TypeConverter
    fun masterConfigToString(list: List<MasterConfigDto>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToMasterConfig(masterConfigString: String?): List<MasterConfigDto>? {
        return masterConfigString?.let {
            Gson().fromJson(
                it,
                object : TypeToken<List<MasterConfigDto>>() {}.type
            )
        }
    }

    @TypeConverter
    fun stringToMasterPortfolio(masterPortfolioString: String?): List<PortfolioDto>? {
        return masterPortfolioString?.let {
            Gson().fromJson(
                it,
                object : TypeToken<List<PortfolioDto>>() {}.type
            )
        }
    }

    @TypeConverter
    fun projectsToString(list: List<ProjectDto>?): String? {
        return list?.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun stringToProjects(projectsString: String?): List<ProjectDto>? {
        return projectsString?.let {
            Gson().fromJson(it, object : TypeToken<List<ProjectDto>>() {}.type)
        }
    }
}