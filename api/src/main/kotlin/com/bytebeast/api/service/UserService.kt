package com.bytebeast.api.service
import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
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

    fun update(id: Long, userDto: UserDTO): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }

        val user = existingUser.copy(
            username = userDto.username,
            password = userDto.password
        )
        return userRepository.save(user)
    }

    fun delete(id: Long) = userRepository.deleteById(id)
}
