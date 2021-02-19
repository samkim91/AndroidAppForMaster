package kr.co.soogong.master.data.estimation

import com.google.gson.JsonObject

data class Estimation(
    val additionalInfo: List<AdditionalInfo>,
    val address: String,
    val area: String,
    val category: String,
    val categoryId: Any,
    val createdAt: Long,
    val description: String,
    val detailAddress: String,
    val expectDate: String,
    val expectTransmissionsCount: Int,
    val images: List<ImagePath>,
    val keycode: String,
    val location: String,
    val project: String,
    val refusedMessagesCount: Int,
    val status: String,
    val statusForApp: String,
    val statusForFront: String,
    val statusKr: String,
    val transmissions: Transmissions,
    val userAssigned: Boolean
) {
    companion object {
        fun fromJson(jsonObject: JsonObject) {
            /*
            {
      "additional_info": [],
      "address": "",
      "area": "서울특별시",
      "category": "욕실",
      "category_id": null,
      "created_at": 1607590405000,
      "description": "",
      "detail_address": "",
      "expect_date": "협의",
      "expect_transmissions_count": 0,
      "images": [],
      "keycode": "wJv7RCzhSHQNuqjX",
      "location": "강동구",
      "project": "욕실 - 예시2",
      "refused_messages_count": 0,
      "status": "closed",
      "status_for_app": null,
      "status_for_front": "평가완료",
      "status_kr": "최종완료",
      "transmissions": {
        "branch_keycode": "e40de2450a27563a",
        "message": {
          "contents": "인건비: 10000 원 <br/>자재비: 50000 원<br/>출장비: 200000 원<br/>위 비용을 기준으로 산정한 견적입니다.<br/>마스터 코멘트 부분입니다2.",
          "price_in_number": 260000,
          "status": "customer_selected",
          "price_detail": [
            {
              "price_in_number": 10000,
              "price_type": "personnel"
            },
            {
              "price_in_number": 50000,
              "price_type": "material"
            },
            {
              "price_in_number": 200000,
              "price_type": "trip"
            }
          ]
        },
        "status": "accepted"
      },
      "user_assigned": false
    },
             */
        }
    }
}