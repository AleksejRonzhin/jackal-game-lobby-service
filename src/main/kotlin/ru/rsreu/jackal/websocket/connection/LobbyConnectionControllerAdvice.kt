package ru.rsreu.jackal.websocket.connection

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.exception.LobbyNotFoundForUserConnectionInfoException
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.connection.dto.ConnectedErrorType
import ru.rsreu.jackal.websocket.connection.dto.ConnectionErrorResponse

@ControllerAdvice(basePackageClasses = [LobbyConnectionController::class])
class LobbyConnectionControllerAdvice(private val wsUtil: WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            ConnectionErrorResponse(connectionErrorType = ConnectedErrorType.INVALID_TOKEN)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundForUserConnectionInfoException::class)
    fun handleNotFindLobbyForUserConnectionInfoException(exception: LobbyNotFoundForUserConnectionInfoException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            ConnectionErrorResponse(connectionErrorType = ConnectedErrorType.NOT_FOUND_USER_FOR_THIS_CONNECTION_INFO)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.wsSendingUserId,
            ConnectionErrorResponse(connectionErrorType = ConnectedErrorType.LOBBY_NOT_EXISTS)
        )
    }

}