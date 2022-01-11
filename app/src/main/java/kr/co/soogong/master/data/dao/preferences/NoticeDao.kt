package kr.co.soogong.master.data.dao.preferences

import androidx.room.*
import io.reactivex.Single
import kr.co.soogong.master.data.dto.mypage.NoticeDto

@Dao
interface NoticeDao {
    @Query("SELECT * FROM Notice ORDER BY id DESC")
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