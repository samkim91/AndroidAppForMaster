package kr.co.soogong.master.data.dao.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.dto.profile.MajorDto
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto

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
    fun masterPortfolioToString(list: List<PortfolioDto>?): String? {
        return list?.let { Gson().toJson(it) }
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
    fun reviewToString(list: List<ReviewDto>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToReview(reviewString: String?): List<ReviewDto>? {
        return reviewString?.let {
            Gson().fromJson(
                it,
                object : TypeToken<List<ReviewDto>>() {}.type
            )
        }
    }

    @TypeConverter
    fun projectsToString(list: List<MajorDto>?): String? {
        return list?.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun stringToProjects(projectsString: String?): List<MajorDto>? {
        return projectsString?.let {
            Gson().fromJson(it, object : TypeToken<List<MajorDto>>() {}.type)
        }
    }
}