package com.bytebeast.api.repository

import com.bytebeast.api.model.Form
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface FormRepository : JpaRepository<Form, Long> {
    fun findByName(name: String): Optional<Form>
}