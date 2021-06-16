package kr.co.soogong.master.data.dao.requirement

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.Requirement

@Dao
interface RequirementDao {
    @Query("SELECT * FROM Requirement")
    fun getAllList(): Single<List<RequirementDto>>

    @Query("SELECT * FROM Requirement WHERE status In (:status)")
    fun getListByStatus(status: List<String>): Single<List<RequirementDto>>

    @Query("SELECT * FROM Requirement WHERE id = :id")
    fun getItem(id: Int): Single<RequirementDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(requirement: RequirementDto): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(requirements: List<RequirementDto>): Completable

    @Delete
    fun remove(requirement: RequirementDto): Completable

    @Query("DELETE FROM Requirement")
    fun removeAll(): Completable
}