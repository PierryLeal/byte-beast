package com.bytebeast.api.controller

import com.bytebeast.api.model.User
import com.bytebeast.api.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should return 200 on get all`() {
        userRepository.save(User(username = "john", password = "pass"))

        mockMvc.get("/users")
            .andExpect { status().isOk }
    }

    @Test
    fun `should return 200 on create`() {
        val body = """{"username": "doe", "password": "1234"}"""

        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }.andExpect { status().isOk }
    }
}
