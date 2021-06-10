package kr.co.soogong.master.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.estimation.EstimationConverters
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.data.profile.ProfileConverters
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.requirements.RequirementConverters
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.profile.ProfileDao
import kr.co.soogong.master.domain.requirements.RequirementDao

@Database(
    entities = [Requirement::class, Estimation::class, Profile::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(RequirementConverters::class, EstimationConverters::class, ProfileConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requirementDao(): RequirementDao

    abstract fun estimationDao(): EstimationDao

    abstract fun profileDao(): ProfileDao
}