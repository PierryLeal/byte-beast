package com.bytebeast.api.repository

import com.bytebeast.api.model.Attribute
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AttributeRepository : JpaRepository<Attribute, Long> {
    fun findByName(name: String): Optional<Attribute>
}