package ru.rsreu.jackal.api.lobby.http.lobbiesGetting

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.api.lobby.http.lobbiesGetting.dto.LobbyNotReadyResponse
import ru.rsreu.jackal.api.lobby.websocket.WebSocketUtil
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.responses.GetInfoForStartResponse

@RestControllerAdvice(basePackageClasses = [LobbyInfoController::class])
class LobbyInfoControllerAdvice(private val wsUtil: WebSocketUtil) {
    @ExceptionHandler(UserNotInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInLobbyException(): ResponseEntity<GetInfoForStartResponse> = ResponseEntity.ok(
        GetInfoForStartResponse(responseStatus = HttpResponseStatus.USER_NOT_IN_LOBBY)
    )

    @ExceptionHandler(UserNotIsHostException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserIsNotHostException(): ResponseEntity<GetInfoForStartResponse> = ResponseEntity.ok(
        GetInfoForStartResponse(responseStatus = HttpResponseStatus.USER_NOT_LOBBY_HOST)
    )

    @ExceptionHandler(LobbyInGameException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyInGameException(): ResponseEntity<GetInfoForStartResponse> = ResponseEntity.ok(
        GetInfoForStartResponse(responseStatus = HttpResponseStatus.LOBBY_IN_GAME)
    )

    @ExceptionHandler(LobbyMemberNotReadyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyMemberNotReadyException(): ResponseEntity<GetInfoForStartResponse> = ResponseEntity.ok(
        GetInfoForStartResponse(responseStatus = HttpResponseStatus.LOBBY_MEMBER_NOT_READY)
    )

    @ExceptionHandler(GameNotSelectedException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleGameNotSelectedException(): ResponseEntity<GetInfoForStartResponse> = ResponseEntity.ok(
        GetInfoForStartResponse(responseStatus = HttpResponseStatus.GAME_NOT_SELECTED)
    )

    @ExceptionHandler(UsersNotHaveActiveConnectionException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUsersNotHaveActiveConnectionException(exception: UsersNotHaveActiveConnectionException): ResponseEntity<GetInfoForStartResponse> {
        wsUtil.sendInfoForLobby(
            exception.lobby.id,
            LobbyNotReadyResponse(exception.notConnectedUsersIds, exception.lobby.getAllMembers())
        )
        return ResponseEntity.ok(GetInfoForStartResponse(responseStatus = HttpResponseStatus.LOBBY_MEMBERS_NOT_CONNECTED))
    }

    @ExceptionHandler(LobbyAlreadyGettingReadyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyAlreadyGettingReadyException(): ResponseEntity<GetInfoForStartResponse> {
        return ResponseEntity.ok(
            GetInfoForStartResponse(
                responseStatus =
                HttpResponseStatus.LOBBY_ALREADY_GETTING_READY
            )
        )
    }
}