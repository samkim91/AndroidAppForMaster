package kr.co.soogong.master.domain.profile

import androidx.room.*
import kr.co.soogong.master.data.profile.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile WHERE phoneNumber = :id")
    fun getItem(id: String): Profile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}