package com.bytebeast.api.service

import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

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
        val user = User(username = "john", password = "pass")
        every { userRepository.save(user) } returns user

        val result = userService.create(user)

        assertEquals(user, result)
    }

    @Test
    fun `should update a user`() {
        val user = User(1, "john", "pass")
        val updated = User(1, "doe", "1234")
        every { userRepository.findById(1) } returns Optional.of(user)
        every { userRepository.save(any()) } returns updated

        val result = userService.update(1, updated)

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
