package com.bytebeast.api.controller

import com.bytebeast.api.dto.CardDTO
import com.bytebeast.api.service.CardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/card")
class CardController(private val cardService: CardService) {
    @GetMapping
    fun getAll(): ResponseEntity<List<CardDTO>> = ResponseEntity.ok(cardService.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<CardDTO> = ResponseEntity.ok(cardService.findById(id))

    @PostMapping
    fun create(@RequestBody cardDTO: CardDTO): ResponseEntity<CardDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(cardService.create(cardDTO))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        cardService.delete(id)
        return ResponseEntity.noContent().build()
    }
}