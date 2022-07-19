package ru.rsreu.jackal.api.lobby.http.lobbiesGetting

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.LobbyInGameException
import ru.rsreu.jackal.exception.LobbyMemberNotReadyException
import ru.rsreu.jackal.exception.UserNotInLobbyException
import ru.rsreu.jackal.exception.UserNotIsHostException
import ru.rsreu.jackal.shared_models.responses.GetLobbyInfoForStartResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestControllerAdvice(basePackageClasses = [LobbyInfoController::class])
class LobbyInfoControllerAdvice {
    @ExceptionHandler(UserNotInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInLobbyException(): ResponseEntity<GetLobbyInfoForStartResponse> = ResponseEntity.ok(
        GetLobbyInfoForStartResponse(responseStatus = HttpLobbyResponseStatus.USER_NOT_IN_LOBBY)
    )

    @ExceptionHandler(UserNotIsHostException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserIsNotHostException(): ResponseEntity<GetLobbyInfoForStartResponse> = ResponseEntity.ok(
        GetLobbyInfoForStartResponse(responseStatus = HttpLobbyResponseStatus.USER_NOT_LOBBY_HOST)
    )

    @ExceptionHandler(LobbyInGameException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyInGameException(): ResponseEntity<GetLobbyInfoForStartResponse> = ResponseEntity.ok(
        GetLobbyInfoForStartResponse(responseStatus = HttpLobbyResponseStatus.LOBBY_IN_GAME)
    )

    @ExceptionHandler(LobbyMemberNotReadyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyMemberNotReadyException(): ResponseEntity<GetLobbyInfoForStartResponse> = ResponseEntity.ok(
        GetLobbyInfoForStartResponse(responseStatus = HttpLobbyResponseStatus.LOBBY_MEMBER_NOT_READY)
    )
}