package ru.rsreu.jackal.websocket

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.models.LobbyMemberInfo
import ru.rsreu.jackal.service.LobbyService
import ru.rsreu.jackal.websocket.dto.*

@Controller
class LobbyWebSocketController(
    private val template: SimpMessagingTemplate,
    private val lobbyService: LobbyService,
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {
    @MessageMapping("/connect/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun connectToLobby(
        @DestinationVariable lobbyId: Long,
        authentication: Authentication
    ): UserConnectedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
        val (hostId, members) = lobbyService.connectUserAndGetHostIdAndAllMembers(userId, lobbyId)
        sendInfoForOne(userId, formUserConnectedInfoForOne(members, hostId))
        return formUserConnectedInfoForAll(authentication)
    }

    private fun formUserConnectedInfoForAll(authentication: Authentication) = UserConnectedInfoForAllResponse(
        connectedUserId = authentication.principal.toString().toLong()
    )

    private fun sendInfoForOne(
        userId: Long, body: Any
    ) {
        template.convertAndSend(webSocketParametersConfiguration.subscriptionUserPattern + userId, body)
    }

    private fun formUserConnectedInfoForOne(members: List<LobbyMemberInfo>, hostId: Long) =
        UserConnectedInfoForOneResponse(
            usersInLobby = members,
            hostId = hostId
        )

    private fun validateTokenForLobbyId(lobbyId: Long, lobbyIdFromAuth: Long, userId: Long) {
        if (lobbyId != lobbyIdFromAuth) {
            throw InvalidForLobbyTokenException(userId)
        }
    }

    @MessageMapping("/leave/{lobbyId}")
    @SendTo("/topic/lobby/{lobbyId}")
    fun leaveFromLobby(
        @DestinationVariable lobbyId: Long,
        authentication: Authentication
    ): UserLeavedInfoForAllResponse {
        val userId = authentication.principal.toString().toLong()
        validateTokenForLobbyId(lobbyId, authentication.credentials.toString().toLong(), userId)
        val newHostId = lobbyService.disconnectUserAndGetHostId(userId, lobbyId)
        sendInfoForOne(userId, UserLeavedInfoForOneResponse())
        return UserLeavedInfoForAllResponse(
            WebSocketResponseType.LEAVED_INFO_FOR_ALL,
            userId,
            newHostId
        )
    }
}