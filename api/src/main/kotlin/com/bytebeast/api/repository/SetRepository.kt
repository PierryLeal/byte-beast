package com.bytebeast.api.repository

import com.bytebeast.api.model.Sets
import org.springframework.data.jpa.repository.JpaRepository

interface SetRepository : JpaRepository<Sets, String>