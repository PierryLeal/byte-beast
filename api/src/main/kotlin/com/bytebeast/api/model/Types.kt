package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@Table(name = "types")
data class Types (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String
)
