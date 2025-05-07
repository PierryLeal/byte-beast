package com.bytebeast.api.controller

import com.bytebeast.api.dto.PartialUserDTO
import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.dto.UserResponseDTO
import com.bytebeast.api.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<UserResponseDTO>> =
        ResponseEntity.ok(userService.findAll().map { UserResponseDTO(it.id, it.username) })

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseDTO> {
        val user = userService.findById(id).orElse(null)
        return if (user != null) ResponseEntity.ok(UserResponseDTO(id = user.id, username = user.username))
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(@RequestBody userDto: UserDTO): ResponseEntity<UserResponseDTO> {
        val createdUser = userService.create(userDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(UserResponseDTO(id = createdUser.id, username = createdUser.username))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody userDto: PartialUserDTO): ResponseEntity<UserResponseDTO> {
        val updatedUser = userService.update(id, userDto)
        return ResponseEntity.ok(UserResponseDTO(id = updatedUser.id, username = updatedUser.username))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
