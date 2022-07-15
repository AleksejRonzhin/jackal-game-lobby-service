package ru.rsreu.jackal.websocket

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.dto.UserConnectedInfoForAllResponse
import ru.rsreu.jackal.websocket.dto.UserConnectedInfoForOneResponse
import ru.rsreu.jackal.websocket.dto.WebSocketResponseType

@Controller
class LobbyWebSocketController(
    private val template: SimpMessagingTemplate,
    private val lobbyService: LobbyService,
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {
    @MessageMapping("/connect/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun connectToLobby(@DestinationVariable lobbyId: Long, authentication: Authentication):
            UserConnectedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
        lobbyService.connectUser(userId)
        sendUserConnectedInfoForOne(userId, formUserConnectedInfoForOne(lobbyId, userId))
        return formUserConnectedInfoForAll(authentication)
    }

    private fun formUserConnectedInfoForAll(authentication: Authentication) =
        UserConnectedInfoForAllResponse(
            WebSocketResponseType.CONNECTED_INFO_FOR_ALL,
            authentication.principal.toString().toLong()
        )

    private fun sendUserConnectedInfoForOne(
        userId: Long,
        userConnectedInfoForOneResponse: UserConnectedInfoForOneResponse
    ) {
        template.convertAndSend(
            webSocketParametersConfiguration.subscriptionUserPattern + userId,
            userConnectedInfoForOneResponse
        )
    }

    private fun formUserConnectedInfoForOne(lobbyId: Long, userId: Long) = UserConnectedInfoForOneResponse(
        WebSocketResponseType.CONNECTED_INFO_FOR_ONE,
        lobbyService.getAllLobbyMembers(lobbyId) ?: throw LobbyNotFoundException(userId)
    )

    private fun validateTokenForLobbyId(lobbyId: Long, lobbyIdFromAuth: Long, userId: Long) {
        if (lobbyId != lobbyIdFromAuth) {
            throw InvalidForLobbyTokenException(userId)
        }
    }
}