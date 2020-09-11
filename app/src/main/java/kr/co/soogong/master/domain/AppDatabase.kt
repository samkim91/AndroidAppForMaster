package kr.co.soogong.master.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.requirements.RequirementConverters
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.data.user.UserConverters
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao

@Database(
    entities = [Requirement::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RequirementConverters::class, UserConverters::class)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun materialDao(): MaterialDao

    abstract fun requirementDao(): RequirementDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "soogong-master.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        /*
                        GlobalScope.launch {
                            val list = JsonInit.loadData(context)
                            if (!list.isNullOrEmpty()) {
                                list.forEach {
                                    getInstance(context).requirementDao().insert(it)
                                }
                            }
                        }
                        */
                    }
                })
                .build()
        }

        const val TAG = "AppDatabase"
    }
}