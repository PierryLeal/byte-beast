package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@IdClass(CardColorId::class)
@Table(name = "card_colors")
data class CardColor(
    @Id
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    val card: Card,

    @Id
    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    val color: Color
)
