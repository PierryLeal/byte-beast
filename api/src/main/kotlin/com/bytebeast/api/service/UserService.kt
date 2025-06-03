package com.bytebeast.api.service

import com.bytebeast.api.dto.PartialUserDTO
import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.exception.BadRequestException
import com.bytebeast.api.exception.ConflictException
import com.bytebeast.api.exception.NotFoundException
import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): User {
        val user = userRepository.findById(id).orElseThrow { NotFoundException("User not found!") }
        return user
    }

    fun create(userDto: UserDTO): User {
        if (userRepository.existsByUsername(userDto.username)) throw ConflictException("User already registered!")

        isValidPassword(userDto.password)
        val hashedPassword = passwordEncoder.encode(userDto.password)
        val user = User(
            username = userDto.username,
            password = hashedPassword
        )
        return userRepository.save(user)
    }

    fun update(id: Long, userDto: PartialUserDTO): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }
        isValidPassword(userDto.password)
        val hashedPassword =
            if (userDto.password !== null) passwordEncoder.encode(userDto.password) else existingUser.password
        val user = existingUser.copy(
            username = userDto.username ?: existingUser.username,
            password = hashedPassword
        )
        return userRepository.save(user)
    }

    fun delete(id: Long) {
        val user = userRepository.findById(id).orElseThrow { NotFoundException("User not found!") }
        userRepository.delete(user)
    }

    fun isValidPassword(password: String?) {
        val regex = Regex("""^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>/?]).{8,}$""")
        if (!regex.matches(
                password ?: ""
            )
        ) throw BadRequestException("Invalid password. It must contain at least 8 characters, including one saved letter, one number, and one special character.")
    }
}
