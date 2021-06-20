package kr.co.soogong.master.data.dao.profile

import androidx.room.*
import kr.co.soogong.master.data.dto.profile.MasterDto

@Dao
interface MasterDao {
    @Query("SELECT * FROM Master WHERE uid = :uid")
    fun getItemByUid(uid: String): MasterDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(masterDto: MasterDto)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(masterDto: MasterDto)

    @Delete
    suspend fun delete(masterDto: MasterDto)

    @Query("DELETE FROM Master")
    fun removeAll()
}