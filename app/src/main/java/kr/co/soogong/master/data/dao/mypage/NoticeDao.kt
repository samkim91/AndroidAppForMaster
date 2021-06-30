package kr.co.soogong.master.data.dao.mypage

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.mypage.Notice

@Dao
interface NoticeDao {
    @Query("SELECT * FROM Notice WHERE id = :id")
    fun getById(id: Int): Maybe<Notice>

    @Query("SELECT * FROM Notice ORDER BY id DESC")
    fun getAll(): Single<List<Notice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notice: Notice)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg notices: Notice)

    @Query("UPDATE Notice SET isNew = :isNew WHERE id = :id")
    fun updateRead(id: Int, isNew: Boolean)

    @Query("DELETE FROM Notice")
    fun removeAll()
}