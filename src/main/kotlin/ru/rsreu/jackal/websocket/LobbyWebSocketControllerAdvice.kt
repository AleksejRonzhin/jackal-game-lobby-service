package ru.rsreu.jackal.websocket

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.configuration.WebSocketParametersConfiguration
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.websocket.dto.ConnectedErrorType
import ru.rsreu.jackal.websocket.dto.ConnectionErrorResponse
import ru.rsreu.jackal.websocket.dto.LeaveErrorType
import ru.rsreu.jackal.websocket.dto.LeavingErrorResponse

@ControllerAdvice
class LobbyWebSocketControllerAdvice(
    private val template: SimpMessagingTemplate,
    private val webSocketParametersConfiguration: WebSocketParametersConfiguration
) {

    @MessageExceptionHandler(InvalidForLobbyTokenException::class) // TODO Response type
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

    @MessageExceptionHandler(LobbyNotFoundException::class) // TODO Response type
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        template.convertAndSend(
            formUserDestination(exception.wsSendingUserId),
            ConnectionErrorResponse(connectedErrorType = ConnectedErrorType.LOBBY_NOT_EXISTS)
        )
    }

    @MessageExceptionHandler(UserNotFoundInAnyLobbyException::class)
    fun handleUserNotFoundInAnyLobbyException(exception: UserNotFoundInAnyLobbyException) {
        template.convertAndSend(
            formUserDestination(exception.userId),
            LeavingErrorResponse(leaveErrorType = LeaveErrorType.NOT_IN_LOBBY)
        )
    }

    @MessageExceptionHandler(AttemptToLeaveFromLobbyInGameException::class)
    fun handleAttemptToLeaveFromLobbyInGameException(exception: AttemptToLeaveFromLobbyInGameException) {
        template.convertAndSend(
            formUserDestination(exception.userId),
            LeavingErrorResponse(leaveErrorType = LeaveErrorType.USER_IN_GAME)
        )
    }

    private fun formUserDestination(userId: Long) =
        webSocketParametersConfiguration.subscriptionUserPattern + userId
}