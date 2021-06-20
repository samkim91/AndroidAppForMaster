package kr.co.soogong.master.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.soogong.master.contract.AppDatabaseContract
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dao.profile.ProfileDao
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.MasterDtoConverters
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.RequirementDtoConverters
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.ProfileConverters

@Database(
    entities = [RequirementDto::class, MasterDto::class, Profile::class],
    version = AppDatabaseContract.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    RequirementDtoConverters::class,
    MasterDtoConverters::class,
    ProfileConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requirementDao(): RequirementDao

    abstract fun masterDao(): MasterDao

    abstract fun profileDao(): ProfileDao
}