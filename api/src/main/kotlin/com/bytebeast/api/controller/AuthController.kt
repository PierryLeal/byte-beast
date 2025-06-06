package com.bytebeast.api.controller

import com.bytebeast.api.dto.AuthDTO
import com.bytebeast.api.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: AuthDTO.AuthRequestDTO): ResponseEntity<AuthDTO.AuthResponseDTO> {
        return ResponseEntity.ok(authService.login(request))
    }
}