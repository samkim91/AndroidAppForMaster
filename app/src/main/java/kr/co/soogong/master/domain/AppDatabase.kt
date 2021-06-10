package kr.co.soogong.master.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.soogong.master.data.model.requirement.Estimation
import kr.co.soogong.master.data.model.requirement.EstimationConverters
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.ProfileConverters
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.data.model.requirement.RequirementConverters
import kr.co.soogong.master.data.dao.estimation.EstimationDao
import kr.co.soogong.master.data.dao.profile.ProfileDao
import kr.co.soogong.master.data.dao.requirement.RequirementDao

@Database(
    entities = [Requirement::class, Estimation::class, Profile::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(RequirementConverters::class, EstimationConverters::class, ProfileConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requirementDao(): RequirementDao

    abstract fun estimationDao(): EstimationDao

    abstract fun profileDao(): ProfileDao
}