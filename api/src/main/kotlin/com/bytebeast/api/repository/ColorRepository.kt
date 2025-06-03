package com.bytebeast.api.repository

import com.bytebeast.api.model.Color
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ColorRepository : JpaRepository<Color, Long> {
    fun findByName(name: String ): Optional<Color>
}