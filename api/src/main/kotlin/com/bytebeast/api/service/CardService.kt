package com.bytebeast.api.service

import com.bytebeast.api.dto.CardDTO
import com.bytebeast.api.model.*
import com.bytebeast.api.repository.*
import org.springframework.stereotype.Service
import java.util.*

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
    fun findAll(): List<Card> = cardRepository.findAll()

    fun findById(id: String): Optional<Card> = cardRepository.findById(id)

    fun create(cardDto: CardDTO): CardDTO {
        print(cardDto)
        if (cardRepository.existsById(cardDto.id)) {
            throw IllegalArgumentException("Card already registered!")
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

        fun getOrCreateSet(sets: Sets): Sets =
            setRepository.findById(sets.id).orElseGet {
                setRepository.save(Sets(id = sets.id, name = sets.name))
            }


        val savedCard = cardRepository.save(
            Card(
                id = cardDto.id,
                name = cardDto.name,
                level = getOrCreateLevel(cardDto.level),
                cardType = getOrCreateCardType(cardDto.cardType),
                formId = getOrCreateForm(cardDto.form),
                attributeId = getOrCreateAttribute(cardDto.attribute),
                typeId = getOrCreateType(cardDto.type),
                dp = cardDto.dp,
                playCost = cardDto.playCost,
                digivolveCost1 = cardDto.digivolveCost1,
                digivolveCost2 = cardDto.digivolveCost2,
                effect = cardDto.effect,
                inheritedEffect = cardDto.inheritedEffect,
                securityEffect = cardDto.securityEffect,
                notes = cardDto.notes,
                setId = getOrCreateSet(cardDto.set)
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

    fun delete(id: String) = cardRepository.deleteById(id)
}