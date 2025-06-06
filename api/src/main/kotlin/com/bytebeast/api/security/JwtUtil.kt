package com.bytebeast.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import javax.crypto.SecretKey


@Component
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    fun generateToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMs)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return getClaims(token)?.subject
    }

    fun isValid(token: String): Boolean {
        val claims = getClaims(token)
        return claims != null && claims.expiration.after(Date())
    }

    private fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }
}