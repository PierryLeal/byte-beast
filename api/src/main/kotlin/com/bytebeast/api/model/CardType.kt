package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@Table(name = "card_types")
data class CardType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String
)
