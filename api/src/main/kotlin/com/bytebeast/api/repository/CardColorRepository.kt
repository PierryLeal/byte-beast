package com.bytebeast.api.repository

import com.bytebeast.api.model.CardColor
import org.springframework.data.jpa.repository.JpaRepository

interface CardColorRepository : JpaRepository<CardColor, String>