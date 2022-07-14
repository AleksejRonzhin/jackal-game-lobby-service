package ru.rsreu.jackal.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyResponse
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyStatus

@RestControllerAdvice
class LobbyControllerAdvice {

    @ExceptionHandler(UserAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserAlreadyInLobbyException(): ResponseEntity<PreConnectLobbyResponse> =
        ResponseEntity.ok(
            PreConnectLobbyResponse(responseStatus = PreConnectLobbyStatus.USER_ALREADY_IN_LOBBY)
        )

    @ExceptionHandler(NotUniqueLobbyTitleException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleNotUniqueLobbyTitleException(): ResponseEntity<PreConnectLobbyResponse> =
        ResponseEntity.ok(
            PreConnectLobbyResponse(responseStatus = PreConnectLobbyStatus.NOT_UNIQUE_LOBBY_TITLE)
        )

    @ExceptionHandler(LobbyNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyNotFoundException(): ResponseEntity<PreConnectLobbyResponse> =
        ResponseEntity.ok(
            PreConnectLobbyResponse(responseStatus = PreConnectLobbyStatus.LOBBY_NOT_FOUND)
        )

    @ExceptionHandler(WrongLobbyPasswordException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleWrongLobbyPasswordException(): ResponseEntity<PreConnectLobbyResponse> =
        ResponseEntity.ok(
            PreConnectLobbyResponse(responseStatus = PreConnectLobbyStatus.WRONG_PASSWORD)
        )

    @ExceptionHandler(UserInBlackListException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserInBlackListException(): ResponseEntity<PreConnectLobbyResponse> =
        ResponseEntity.ok(
            PreConnectLobbyResponse(responseStatus = PreConnectLobbyStatus.USER_IN_LOBBY_BLACK_LIST)
        )
}