package com.bytebeast.api.dto

import com.bytebeast.api.model.Sets

data class CardDTO(
    val id: String,
    val name: String,
    val level: String,
    val cardType: String,
    val form: String,
    val attribute: String?,
    val type: String,
    val dp: Int?,
    val colors: List<String>,
    val playCost: Int?,
    val digivolveCost1: String?,
    val digivolveCost2: String?,
    val effect: String?,
    val inheritedEffect: String?,
    val securityEffect: String?,
    val notes: String?,
    val set: Sets,
)
