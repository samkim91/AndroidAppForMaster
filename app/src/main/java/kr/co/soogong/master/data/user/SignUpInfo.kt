package kr.co.soogong.master.data.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpInfo(
    // 가입자 이메일
    val email: String,
    // 비밀번호
    val password: String,
    // 비밀번호 확인
    val passwordConfirmation: String,
    // 닉네임/업체명
    val username: String,
    // 해당 Oauth로 부터 받은 token
    val phoneNumber: String,
    // 유저 타입(가입 종류) master 고정 값
    val customerType: String = "master",
    // 시/도 구분(Short)
    val area: String, // "서울"
    // 시/군/구 구분
    val location: String, // "마포구"
    // 사업자 등록번호
    val businessNumber: String,
    // 연락처
    val tel: String,
    // 주소
    val address: String,
    // 세부주소
    val detailAddress: String,
    // 업체 소개
    val description: String = "",
    // 개업일(년-월-일)
    val openDate: String,
    // 모바일/웹에서 신청 시 [requested]
    val status: String
) : Parcelable