package com.bytebeast.api.repository

import com.bytebeast.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<User>
}