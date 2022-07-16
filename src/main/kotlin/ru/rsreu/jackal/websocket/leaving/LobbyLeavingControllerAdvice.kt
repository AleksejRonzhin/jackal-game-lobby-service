package ru.rsreu.jackal.websocket.leaving

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.exception.AttemptToLeaveFromLobbyInGameException
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.exception.UserNotFoundInAnyLobbyException
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.leaving.dto.UserLeavingErrorResponse
import ru.rsreu.jackal.websocket.leaving.dto.UserLeavingErrorType

@ControllerAdvice(basePackageClasses = [LobbyLeavingController::class])
class LobbyLeavingControllerAdvice(private val wsUtil: WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserLeavingErrorResponse(userLeavingErrorType = UserLeavingErrorType.INVALID_TOKEN)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserLeavingErrorResponse(userLeavingErrorType = UserLeavingErrorType.LOBBY_NOT_EXISTS)
        )
    }

    @MessageExceptionHandler(UserNotFoundInAnyLobbyException::class)
    fun handleUserNotFoundInAnyLobbyException(exception: UserNotFoundInAnyLobbyException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserLeavingErrorResponse(userLeavingErrorType = UserLeavingErrorType.ALREADY_NOT_IN_LOBBY)
        )
    }

    @MessageExceptionHandler(AttemptToLeaveFromLobbyInGameException::class)
    fun handleAttemptToLeaveFromLobbyInGameException(exception: AttemptToLeaveFromLobbyInGameException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserLeavingErrorResponse(userLeavingErrorType = UserLeavingErrorType.USER_IN_GAME)
        )
    }
}