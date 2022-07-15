package ru.rsreu.jackal.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.responses.*

@RestControllerAdvice
class LobbyControllerAdvice { // TODO 1 экзепшен здесь может прокинуть только один класс ответа, возможно стоит класть в экзепшн объект ответа
    @ExceptionHandler(HostAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleHostAlreadyInLobbyException(): ResponseEntity<CreateLobbyResponse> = ResponseEntity.ok(
        CreateLobbyResponse(responseStatus = CreateLobbyStatus.HOST_ALREADY_IN_LOBBY)
    )

    @ExceptionHandler(NotUniqueLobbyTitleException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleNotUniqueLobbyTitleException(): ResponseEntity<CreateLobbyResponse> = ResponseEntity.ok(
        CreateLobbyResponse(responseStatus = CreateLobbyStatus.NOT_UNIQUE_LOBBY_TITLE)
    )

    @ExceptionHandler(UserAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserAlreadyInLobbyException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = JoinLobbyStatus.USER_ALREADY_IN_LOBBY)
    )

    @ExceptionHandler(LobbyNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyNotFoundException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = JoinLobbyStatus.LOBBY_NOT_FOUND)
    )

    @ExceptionHandler(WrongLobbyPasswordException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleWrongLobbyPasswordException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = JoinLobbyStatus.WRONG_PASSWORD)
    )

    @ExceptionHandler(UserInBlackListException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserInBlackListException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = JoinLobbyStatus.USER_IN_LOBBY_BLACK_LIST)
    )

    @ExceptionHandler(UserNotInAnyLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInAnyLobbyException(): ResponseEntity<GetLobbyConnectionInfoResponse> = ResponseEntity.ok(
        GetLobbyConnectionInfoResponse(responseStatus = GetLobbyConnectionInfoStatus.USER_NOT_IN_ANY_LOBBY)
    )
}