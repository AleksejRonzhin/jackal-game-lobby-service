package ru.rsreu.jackal.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}") private val secretKeyStringRepresentation: String
) {
    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor(secretKeyStringRepresentation.toByteArray(StandardCharsets.UTF_8))

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    private val lobbyIdJwtKey = "lobby_id"

    fun createAccessToken(lobbyId: Long, userId: Long): String = Jwts.builder()
        .setClaims(formAccessClaims(lobbyId, userId))
        .signWith(secretKey)
        .setHeaderParam("typ", "JWT")
        .compact()

    private fun formAccessClaims(lobbyId: Long, userId: Long): Claims {
        val claims = Jwts.claims().setSubject(userId.toString())
        claims[lobbyIdJwtKey] = lobbyId
        return claims
    }

    fun getJwsClaims(jwt: String): Jws<Claims> = jwtParser.parseClaimsJws(jwt)

    fun getAuthenticationFromJwt(jwtToken: String): Authentication? {
        val claims = getJwsClaims(jwtToken).body
        val authentication = PreAuthenticatedAuthenticationToken(
            claims.subject,
            claims[lobbyIdJwtKey]
        )
        authentication.isAuthenticated = true
        return authentication
    }
}