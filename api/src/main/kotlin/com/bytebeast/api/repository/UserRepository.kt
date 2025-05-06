package com.bytebeast.api.repository

import com.bytebeast.api.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>