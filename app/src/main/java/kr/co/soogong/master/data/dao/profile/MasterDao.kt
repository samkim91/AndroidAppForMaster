package kr.co.soogong.master.data.dao.profile

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto

@Dao
interface MasterDao {
    @Query("SELECT * FROM Master WHERE uid = :uid")
    fun getByUid(uid: String?): Maybe<MasterDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(masterDto: MasterDto)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(masterDto: MasterDto)

    @Query("UPDATE Master SET masterPortfolios = :portfolioDtos WHERE uid = :uid")
    fun updatePortfolios(uid: String?, portfolioDtos: List<PortfolioDto>)

    @Delete
    suspend fun delete(masterDto: MasterDto)

    @Query("DELETE FROM Master")
    fun removeAll()
}