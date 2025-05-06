package com.bytebeast.api.service

import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.dto.UserResponseDTO
import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): Optional<User> = userRepository.findById(id)


    fun create(userDto: UserDTO): User {
        val hashedPassword = passwordEncoder.encode(userDto.password)
        val user = User(
            username = userDto.username,
            password = hashedPassword
        )
        return userRepository.save(user)
    }

    fun update(id: Long, user: User): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }

        val updatedUser = existingUser.copy(
            username = user.username,
            password = user.password
        )
        return userRepository.save(updatedUser)
    }

    fun delete(id: Long) = userRepository.deleteById(id)
}
