package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@Table(name = "sets")
data class Sets (
    @Id
    val id: String,

    @Column
    val name: String
)
