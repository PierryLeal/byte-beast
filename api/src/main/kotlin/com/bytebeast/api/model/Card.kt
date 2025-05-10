package com.bytebeast.api.model

import jakarta.persistence.*

@Entity
@Table(name = "cards")
data class Card(
    @Id
    val id: String,

    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "level_id")
    val level: Level,

    @ManyToOne
    @JoinColumn(name = "card_type_id")
    val cardType: CardType,

    @ManyToOne
    @JoinColumn(name = "form_id")
    val formId: Form,

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    val attributeId: Attribute?,

    @ManyToOne
    @JoinColumn(name = "type_id")
    val typeId: Types,

    @Column
    val dp: Int?,

    @Column(name = "play_cost")
    val playCost: Int?,

    @Column(name = "digivolve_cost1", columnDefinition = "TEXT")
    val digivolveCost1: String?,

    @Column(name = "digivolve_cost2",columnDefinition = "TEXT")
    val digivolveCost2: String?,

    @Column(columnDefinition = "TEXT")
    val effect: String?,

    @Column(name = "inherited_effect",columnDefinition = "TEXT")
    val inheritedEffect: String?,

    @Column(name = "security_effect", columnDefinition = "TEXT")
    val securityEffect: String?,

    @Column(columnDefinition = "TEXT")
    val notes: String?,

    @ManyToOne
    @JoinColumn(name = "set_id")
    val setId: Sets,
)