package com.bytebeast.api.service

import com.bytebeast.api.dto.PartialUserDTO
import com.bytebeast.api.dto.UserDTO
import com.bytebeast.api.exception.BadRequestException
import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import kotlin.test.assertEquals

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        passwordEncoder = mockk()
        userService = UserService(userRepository, passwordEncoder)
    }

    @Test
    fun `should return all users`() {
        val users = listOf(User(1, "john", "pass"))
        every { userRepository.findAll() } returns users

        val result = userService.findAll()

        assertEquals(users, result)
    }

    @Test
    fun `should return user by id`() {
        val user = User(1, "john", "senha123")
        every { userRepository.findById(1) } returns Optional.of(user)

        val result = userService.findById(1)

        assertEquals(user, result)
        verify(exactly = 1) { userRepository.findById(1) }
    }

    @Test
    fun `should create a user`() {
        val userDto = UserDTO(username = "john", password = "pass")
        val hashedPassword = "hashed_pass"

        every { passwordEncoder.encode("pass") } returns hashedPassword
        every { userRepository.save(any()) } answers { firstArg() }

        val result = userService.create(userDto)

        assertEquals("john", result.username)
        assertEquals(hashedPassword, result.password)
    }

    @Test
    fun `should update a user`() {
        val existingUser = User(1, "john", "pass")
        val updatedDto = PartialUserDTO(username = "doe", password = "1234")

        every { userRepository.findById(1) } returns Optional.of(existingUser)
        every { passwordEncoder.encode("1234") } returns "hashed_1234"
        every { userRepository.save(any()) } answers { firstArg() }

        val result = userService.update(1, updatedDto)

        assertEquals("doe", result.username)
        assertEquals("hashed_1234", result.password)
    }

    @Test
    fun `should delete a user`() {
        val user = User(1, "john", "pass")
        every { userRepository.findById(1) } returns Optional.of(user)
        every { userRepository.delete(user) } just Runs

        userService.delete(1)

        verify { userRepository.delete(user) }
    }

    @Test
    fun `should throw exception when updating with no data`() {
        val user = User(1, "john", "pass")
        val emptyUpdate = PartialUserDTO(username = null, password = null)

        every { userRepository.findById(1) } returns Optional.of(user)

        assertThrows<BadRequestException> {
            userService.update(1, emptyUpdate)
        }
    }
}
