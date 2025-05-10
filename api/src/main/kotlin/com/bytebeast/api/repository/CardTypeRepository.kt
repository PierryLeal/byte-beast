package com.bytebeast.api.repository

import com.bytebeast.api.model.CardType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CardTypeRepository : JpaRepository<CardType, Long> {
    fun findByName(name: String): Optional<CardType>
}