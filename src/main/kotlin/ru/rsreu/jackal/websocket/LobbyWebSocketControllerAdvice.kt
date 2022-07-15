package ru.rsreu.jackal.websocket

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.exception.LobbyNotFoundForUserConnectionInfoException
import ru.rsreu.jackal.websocket.dto.ConnectedErrorType
import ru.rsreu.jackal.websocket.dto.ConnectionErrorResponse

@ControllerAdvice
class LobbyWebSocketControllerAdvice(
    private val template: SimpMessagingTemplate,
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {

    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        template.convertAndSend(
            formUserDestination(exception.userId),
            ConnectionErrorResponse(connectedErrorType = ConnectedErrorType.INVALID_TOKEN)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundForUserConnectionInfoException::class)
    fun handleNotFindLobbyForUserConnectionInfoException(exception: LobbyNotFoundForUserConnectionInfoException) {
        template.convertAndSend(
            formUserDestination(exception.userId),
            ConnectionErrorResponse(connectedErrorType = ConnectedErrorType.NOT_FIND_USER_FOR_THIS_CONNECTION_INFO)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        template.convertAndSend(
            formUserDestination(exception.wsSendingUserId),
            ConnectionErrorResponse(connectedErrorType = ConnectedErrorType.LOBBY_NOT_EXISTS)
        )
    }

    private fun formUserDestination(userId: Long) =
        webSocketParametersConfiguration.subscriptionUserPattern + userId
}