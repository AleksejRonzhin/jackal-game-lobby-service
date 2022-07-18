package ru.rsreu.jackal.api.lobby.websocket.connection

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.api.lobby.websocket.connection.dto.UserConnectionErrorResponse
import ru.rsreu.jackal.api.lobby.websocket.connection.dto.UserConnectionErrorType
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.exception.LobbyNotFoundForUserConnectionInfoException

@ControllerAdvice(basePackageClasses = [LobbyConnectionController::class])
class LobbyConnectionControllerAdvice(private val wsUtil: ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserConnectionErrorResponse(userConnectionErrorType = UserConnectionErrorType.INVALID_TOKEN)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundForUserConnectionInfoException::class)
    fun handleNotFindLobbyForUserConnectionInfoException(exception: LobbyNotFoundForUserConnectionInfoException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserConnectionErrorResponse(userConnectionErrorType = UserConnectionErrorType.NOT_FOUND_USER_FOR_THIS_CONNECTION_INFO)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserConnectionErrorResponse(userConnectionErrorType = UserConnectionErrorType.LOBBY_NOT_EXISTS)
        )
    }
}