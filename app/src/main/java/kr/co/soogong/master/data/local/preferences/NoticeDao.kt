package kr.co.soogong.master.data.local.preferences

import androidx.room.*
import io.reactivex.Single
import kr.co.soogong.master.data.entity.preferences.NoticeDto

@Dao
interface NoticeDao {
    @Query("SELECT * FROM Notice ORDER BY createdAt DESC")
    fun getNotices(): Single<List<NoticeDto>>

    @Query("SELECT * FROM Notice WHERE id = :id")
    fun getNotice(id: Int): Single<NoticeDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNotice(vararg notices: NoticeDto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotice(notice: NoticeDto)

    @Query("UPDATE Notice SET isNew = 0 WHERE id = :id")
    fun updateNoticeIsNew(id: Int)

    @Query("DELETE FROM Notice")
    fun removeAll()
}