package kr.co.soogong.master.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.soogong.master.contract.AppDatabaseContract
import kr.co.soogong.master.data.datasource.local.preferences.NoticeDao
import kr.co.soogong.master.data.datasource.local.profile.MasterDao
import kr.co.soogong.master.data.datasource.local.profile.MasterDtoConverters
import kr.co.soogong.master.data.datasource.local.requirement.RequirementDao
import kr.co.soogong.master.data.datasource.local.requirement.RequirementDtoConverters
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto

@Database(
    entities = [RequirementDto::class, MasterDto::class, NoticeDto::class],
    version = AppDatabaseContract.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    RequirementDtoConverters::class,
    MasterDtoConverters::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requirementDao(): RequirementDao

    abstract fun masterDao(): MasterDao

    abstract fun noticeDao(): NoticeDao
}