package com.bytebeast.api.service

import com.bytebeast.api.dto.AuthDTO.*
import com.bytebeast.api.repository.UserRepository
import com.bytebeast.api.security.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(request: AuthRequestDTO): AuthResponseDTO {
        val auth = UsernamePasswordAuthenticationToken(request.username, request.password)
        authManager.authenticate(auth)
        val token = jwtUtil.generateToken(request.username)
        return AuthResponseDTO(token)
    }
}