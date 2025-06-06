package com.bytebeast.api.dto

class AuthDTO {
    data class AuthRequestDTO(val username: String, val password: String)
    data class AuthResponseDTO(val token: String)
}