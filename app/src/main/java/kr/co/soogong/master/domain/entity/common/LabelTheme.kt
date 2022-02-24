package kr.co.soogong.master.domain.entity.common

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kr.co.soogong.master.R

enum class LabelTheme(
    @DrawableRes val backgroundRes: Int,
    @ColorRes val backgroundTintRes: Int,
    val backgroundTintAlpha: Int,
    @ColorRes val textColorRes: Int,
) {

    // Basic
    BASIC_BLUE(R.drawable.bg_solid_white_radius4, R.color.brand_blue, 25, R.color.brand_blue),
    BASIC_GREY(R.drawable.bg_solid_white_radius4, R.color.background, 255, R.color.grey_4),
    BASIC_RED(R.drawable.bg_solid_white_radius4, R.color.brand_red, 25, R.color.brand_red),
    BASIC_GREEN(R.drawable.bg_solid_white_radius4, R.color.brand_green, 25, R.color.brand_green),

    // Outlined
    OUTLINED_GREY(R.drawable.bg_solid_transparent_stroke_light_grey2_radius4, R.color.light_grey_2, 255, R.color.grey_3),
    OUTLINED_GREY_ROUNDED(R.drawable.bg_solid_transparent_stroke_light_grey2_radius20, R.color.light_grey_2, 255, R.color.grey_3),
    OUTLINED_GREEN(R.drawable.bg_solid_transparent_stroke_light_grey2_radius4, R.color.brand_green, 255, R.color.brand_green),
    OUTLINED_GREEN_ROUNDED(R.drawable.bg_solid_transparent_stroke_light_grey2_radius20, R.color.brand_green, 255, R.color.brand_green),
    OUTLINED_RED(R.drawable.bg_solid_transparent_stroke_light_grey2_radius4, R.color.brand_red, 255, R.color.brand_red),
    OUTLINED_RED_ROUNDED(R.drawable.bg_solid_transparent_stroke_light_grey2_radius20, R.color.brand_red, 255, R.color.brand_red),
    OUTLINED_BLUE(R.drawable.bg_solid_transparent_stroke_light_grey2_radius4, R.color.brand_blue, 255, R.color.brand_blue),
    OUTLINED_BLUE_ROUNDED(R.drawable.bg_solid_transparent_stroke_light_grey2_radius20, R.color.brand_blue, 255, R.color.brand_blue),
}