package ru.rsreu.jackal.websocket.leaving

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.exception.AttemptToLeaveFromLobbyInGameException
import ru.rsreu.jackal.exception.InvalidForLobbyTokenException
import ru.rsreu.jackal.exception.LobbyNotFoundException
import ru.rsreu.jackal.exception.UserNotFoundInAnyLobbyException
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.leaving.dto.LeavingErrorResponse
import ru.rsreu.jackal.websocket.leaving.dto.LeavingErrorType

@ControllerAdvice(basePackageClasses = [LobbyLeavingController::class])
class LobbyLeavingControllerAdvice(private val wsUtil: WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            LeavingErrorResponse(leavingErrorType = LeavingErrorType.INVALID_TOKEN)
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.wsSendingUserId,
            LeavingErrorResponse(leavingErrorType = LeavingErrorType.LOBBY_NOT_EXISTS)
        )
    }

    @MessageExceptionHandler(UserNotFoundInAnyLobbyException::class)
    fun handleUserNotFoundInAnyLobbyException(exception: UserNotFoundInAnyLobbyException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            LeavingErrorResponse(leavingErrorType = LeavingErrorType.ALREADY_NOT_IN_LOBBY)
        )
    }

    @MessageExceptionHandler(AttemptToLeaveFromLobbyInGameException::class)
    fun handleAttemptToLeaveFromLobbyInGameException(exception: AttemptToLeaveFromLobbyInGameException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            LeavingErrorResponse(leavingErrorType = LeavingErrorType.USER_IN_GAME)
        )
    }
}