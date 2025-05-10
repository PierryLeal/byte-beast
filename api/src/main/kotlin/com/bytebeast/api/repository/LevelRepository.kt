package com.bytebeast.api.repository

import com.bytebeast.api.model.Level
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LevelRepository : JpaRepository<Level, Long> {
    fun findByName(name: String): Optional<Level>
}