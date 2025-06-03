package com.bytebeast.api.service

import com.bytebeast.api.dto.CardDTO
import com.bytebeast.api.exception.ConflictException
import com.bytebeast.api.exception.NotFoundException
import com.bytebeast.api.model.*
import com.bytebeast.api.repository.*
import org.springframework.stereotype.Service

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val levelRepository: LevelRepository,
    private val cardTypeRepository: CardTypeRepository,
    private val formRepository: FormRepository,
    private val attributeRepository: AttributeRepository,
    private val typeRepository: TypeRepository,
    private val setRepository: SetRepository,
    private val colorRepository: ColorRepository,
    private val cardColorRepository: CardColorRepository,
) {
    fun findAll(): List<CardDTO> {
       return cardRepository.findAll().map {it.toDTO()}
    }

    fun findById(id: String): CardDTO {
        val cardResponse = cardRepository.findById(id).orElseThrow {
            NotFoundException("Card not found!")
        }
        return cardResponse.toDTO()
    }

    fun create(cardDto: CardDTO): CardDTO {
        if (cardRepository.existsById(cardDto.id)) {
            throw ConflictException("Card already registered!")
        }

        fun getOrCreateLevel(name: String): Level =
            levelRepository.findByName(name).orElseGet { levelRepository.save(Level(name = name)) }

        fun getOrCreateCardType(name: String): CardType =
            cardTypeRepository.findByName(name).orElseGet { cardTypeRepository.save(CardType(name = name)) }

        fun getOrCreateForm(name: String): Form =
            formRepository.findByName(name).orElseGet { formRepository.save(Form(name = name)) }

        fun getOrCreateAttribute(name: String?): Attribute? =
            if (name.isNullOrBlank()) null else attributeRepository.findByName(name)
                .orElseGet { attributeRepository.save(Attribute(name = name)) }

        fun getOrCreateType(name: String): Types =
            typeRepository.findByName(name).orElseGet { typeRepository.save(Types(name = name)) }

        fun getOrCreateSet(sets: Sets): Sets = setRepository.findById(sets.id).orElseGet {
            setRepository.save(Sets(id = sets.id, name = sets.name))
        }

        val savedCard = cardRepository.save(
            Card(
                id = cardDto.id,
                name = cardDto.name,
                level = getOrCreateLevel(cardDto.level),
                cardType = getOrCreateCardType(cardDto.cardType),
                form = getOrCreateForm(cardDto.form),
                attribute = getOrCreateAttribute(cardDto.attribute),
                type = getOrCreateType(cardDto.type),
                dp = cardDto.dp,
                playCost = cardDto.playCost,
                digivolveCost1 = cardDto.digivolveCost1,
                digivolveCost2 = cardDto.digivolveCost2,
                effect = cardDto.effect,
                inheritedEffect = cardDto.inheritedEffect,
                securityEffect = cardDto.securityEffect,
                notes = cardDto.notes,
                set = getOrCreateSet(cardDto.set)
            )
        )

        cardDto.colors.forEach { color ->
            val color = colorRepository.findByName(color).orElseGet {
                colorRepository.save(Color(name = color))
            }
            cardColorRepository.save(CardColor(card = savedCard, color = color))
        }

        return cardDto
    }

    fun delete(id: String) {
        val cardResponse = cardRepository.findById(id).orElseThrow {
            NotFoundException("Card not found!")
        }
        cardRepository.delete(cardResponse)
    }

    fun Card.toDTO() = CardDTO(
        id = id,
        name = name,
        level = level.name,
        cardType = cardType.name,
        form = form.name,
        attribute = attribute?.name,
        type = type.name,
        dp = dp,
        colors = colors.map { it.name },
        playCost = playCost,
        digivolveCost1 = digivolveCost1,
        digivolveCost2 = digivolveCost2,
        effect = effect,
        inheritedEffect = inheritedEffect,
        securityEffect = securityEffect,
        notes = notes,
        set = set
    )
}