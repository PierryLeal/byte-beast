package com.bytebeast.api.repository

import com.bytebeast.api.model.Types
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TypeRepository : JpaRepository<Types, Long> {
    fun findByName(name: String): Optional<Types>
}