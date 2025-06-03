package com.bytebeast.api.repository

import com.bytebeast.api.model.Card
import org.springframework.data.jpa.repository.*

interface CardRepository : JpaRepository<Card, String>