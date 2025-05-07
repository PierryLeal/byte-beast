package com.bytebeast.api.dto

data class UserDTO (
    val username: String,
    val password: String
)

data class PartialUserDTO (
    val username: String? = null,
    val password: String? = null
)

data class UserResponseDTO(
    val id: Long,
    val username: String
)