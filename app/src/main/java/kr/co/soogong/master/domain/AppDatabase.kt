package kr.co.soogong.master.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.estimation.EstimationConverters
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.requirements.RequirementConverters
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.data.user.UserConverters
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao

@Database(
    entities = [Requirement::class, User::class, Estimation::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(RequirementConverters::class, UserConverters::class, EstimationConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requirementDao(): RequirementDao

    abstract fun estimationDao(): EstimationDao

    abstract fun userDao(): UserDao
}