package kr.co.soogong.master.data.dao.profile

import androidx.room.*
import io.reactivex.Maybe
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto

@Dao
interface MasterDao {
    @Query("SELECT * FROM Master WHERE uid = :uid")
    fun getByUid(uid: String?): Maybe<MasterDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(masterDto: MasterDto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(masterDto: MasterDto)

    @Delete
    suspend fun delete(masterDto: MasterDto)

    @Query("DELETE FROM Master")
    fun removeAll()
}