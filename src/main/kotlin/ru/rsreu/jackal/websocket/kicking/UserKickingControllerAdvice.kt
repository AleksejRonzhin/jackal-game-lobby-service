package ru.rsreu.jackal.websocket.kicking

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.websocket.WebSocketUtil
import ru.rsreu.jackal.websocket.kicking.dto.UserKickingErrorResponse
import ru.rsreu.jackal.websocket.kicking.dto.UserKickingErrorType

@ControllerAdvice(basePackageClasses = [UserKickingController::class])
class UserKickingControllerAdvice(private val wsUtil: WebSocketUtil) {
    @MessageExceptionHandler(InvalidForLobbyTokenException::class)
    fun handleInvalidForLobbyTokenException(exception: InvalidForLobbyTokenException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.INVALID_TOKEN
            )
        )
    }

    @MessageExceptionHandler(LobbyNotFoundException::class)
    fun handleLobbyNotFoundException(exception: LobbyNotFoundException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.LOBBY_NOT_FOUND
            )
        )
    }

    @MessageExceptionHandler(NotHostKickAttemptException::class)
    fun handleNotHostKickAttemptException(exception: NotHostKickAttemptException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.NOT_HOST
            )
        )
    }

    @MessageExceptionHandler(HostSelfKickAttemptException::class)
    fun handleHostSelfKickAttemptException(exception: HostSelfKickAttemptException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.HOST_SELF_KICKING
            )
        )
    }

    @MessageExceptionHandler(KickableUserNotInLobbyException::class)
    fun handleKickableUserNotInLobbyException(exception: KickableUserNotInLobbyException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.KICKABLE_NOT_IN_LOBBY
            )
        )
    }

    @MessageExceptionHandler(UserInGameAttemptKickException::class)
    fun handleUserInGameAttemptKickException(exception: UserInGameAttemptKickException) {
        wsUtil.sendInfoForOne(
            exception.userId,
            UserKickingErrorResponse(
                userKickingErrorType = UserKickingErrorType.KICKABLE_IN_GAME
            )
        )
    }
}