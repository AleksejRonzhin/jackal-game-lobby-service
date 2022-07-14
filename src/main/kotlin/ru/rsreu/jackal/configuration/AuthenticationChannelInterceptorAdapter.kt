package ru.rsreu.jackal.configuration

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import ru.rsreu.jackal.jwt.JwtTokenProvider

@Component
class AuthenticationChannelInterceptorAdapter(private val jwtTokenProvider: JwtTokenProvider) : ChannelInterceptor {
    private val authorizationHeader = "Authorization"

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        if (accessor!!.user == null) {
            val authentication = jwtTokenProvider.runCatching {
                getAuthenticationFromJwt(accessor.getFirstNativeHeader(authorizationHeader)!!)
            }.getOrNull()
            accessor.user = authentication
        }
        return message
    }
}