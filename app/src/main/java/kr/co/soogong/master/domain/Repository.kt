package kr.co.soogong.master.domain

import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao

class Repository internal constructor(
    private val estimationDao: EstimationDao,
    private val requirementDao: RequirementDao,
    private val userDao: UserDao,
    private val appSharedPreference: AppSharedPreference
) {
    //region Estimation
    fun getEstimationList() = estimationDao.getAllList()

    fun getEstimation(keycode: String) = estimationDao.getItem(keycode)

    suspend fun insertEstimation(estimation: Estimation) = estimationDao.insert(estimation)

    suspend fun insertEstimation(estimationList: List<Estimation>) =
        estimationDao.insert(estimationList)

    suspend fun removeEstimation(keycode: String) = estimationDao.remove(keycode)

    suspend fun removeAllEstimation() = estimationDao.removeAll()
    //endregion

    //region Requirement
    fun getRequirementList() = requirementDao.getAllList()

    fun getRequirement(keycode: String) = requirementDao.getItem(keycode)

    suspend fun insertRequirement(requirement: Requirement) = requirementDao.insert(requirement)

    suspend fun insertRequirement(requirements: List<Requirement>) =
        requirementDao.insert(requirements)

    suspend fun removeRequirement(keycode: String) = requirementDao.remove(keycode)

    suspend fun removeAllRequirement() = requirementDao.removeAll()
    //endregion Requirement

    //region user
    fun getUserInfo(id: String) = userDao.getItem(id)

    suspend fun insertUserInfo(user: User) = userDao.insert(user)

    suspend fun removeUserInfo(id: String) = userDao.remove(id)

    suspend fun removeAllUserInfo() = userDao.removeAll()
    //endregion user

    //region AppSharedPreference
    fun setBoolean(key: String, value: Boolean) = appSharedPreference.setBoolean(key, value)

    fun getBoolean(key: String, defaultValue: Boolean) =
        appSharedPreference.getBoolean(key, defaultValue)

    fun setInteger(key: String, value: Int) = appSharedPreference.setInteger(key, value)

    fun getInteger(key: String, defaultValue: Int) =
        appSharedPreference.getInteger(key, defaultValue)

    fun setFloat(key: String, value: Float) = appSharedPreference.setFloat(key, value)

    fun getFloat(key: String, defaultValue: Float) = appSharedPreference.getFloat(key, defaultValue)

    fun setString(key: String, value: String) = appSharedPreference.setString(key, value)

    fun getString(key: String, defaultValue: String? = null) =
        appSharedPreference.getString(key, defaultValue)
    //endregion AppSharedPreference
}