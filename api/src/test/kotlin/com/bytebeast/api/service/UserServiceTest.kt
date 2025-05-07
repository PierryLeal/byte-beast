package com.bytebeast.api.service

import com.bytebeast.api.dto.PartialUserDTO
import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val userService = UserService(userRepository, passwordEncoder)

    @Test
    fun `should return all users`() {
        val users = listOf(User(1, "john", "pass"))
        every { userRepository.findAll() } returns users

        val result = userService.findAll()

        assertEquals(users, result)
    }

    @Test
    fun `should return user by id`() {
        val user = User(1, "john", "pass")
        every { userRepository.findById(1) } returns Optional.of(user)

        val result = userService.findById(1)

        assertTrue(result.isPresent)
        assertEquals(user, result.get())
    }

    @Test
    fun `should create a user`() {
        val userDto = UserDTO(username = "john", password = "pass")
        val hashedPassword = "hashed_pass"

        every { passwordEncoder.encode("pass") } returns hashedPassword

        val savedUserSlot = slot<User>()
        every { userRepository.save(capture(savedUserSlot)) } answers { savedUserSlot.captured }

        val result = userService.create(userDto)

        val savedUser = savedUserSlot.captured
        assertEquals("john", savedUser.username)
        assertEquals(hashedPassword, savedUser.password)
        assertEquals(savedUser, result)
    }

    @Test
    fun `should update a user`() {
        val user = User(1, "john", "pass")
        val updated = User(1, "doe", "1234")
        every { userRepository.findById(1) } returns Optional.of(user)
        every { userRepository.save(any()) } returns updated

        val userDto = PartialUserDTO(username = updated.username, password = updated.password)
        val result = userService.update(1, userDto)

        assertEquals("doe", result.username)
        assertEquals("1234", result.password)
    }

    @Test
    fun `should delete a user`() {
        every { userRepository.deleteById(1) } just Runs

        userService.delete(1)

        verify { userRepository.deleteById(1) }
    }
}
