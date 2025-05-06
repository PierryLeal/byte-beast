package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    val username: String = "",

    @Column(nullable = false, length = 100)
    val password: String = ""
)
