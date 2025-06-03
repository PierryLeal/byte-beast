package com.bytebeast.api.model

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class CardColorId(var card: String = "", var color: Long = 0) : Serializable
