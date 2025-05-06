package com.bytebeast.api.dto

data class UserDTO (
    val username: String,
    val password: String
)

data class UserResponseDTO(
    val id: Long,
    val username: String
)