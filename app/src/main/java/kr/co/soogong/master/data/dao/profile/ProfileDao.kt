package kr.co.soogong.master.data.dao.profile

import androidx.room.*
import kr.co.soogong.master.data.model.profile.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile WHERE tel = :id")
    fun getItem(id: String?): Profile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}