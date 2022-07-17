package ru.rsreu.jackal.api.lobby.http.joining

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.*
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus
import ru.rsreu.jackal.shared_models.responses.JoinLobbyResponse

@RestControllerAdvice(basePackageClasses = [LobbyJoiningControllerAdvice::class])
class LobbyJoiningControllerAdvice {
    @ExceptionHandler(UserAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserAlreadyInLobbyException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = HttpLobbyResponseStatus.USER_ALREADY_IN_LOBBY)
    )

    @ExceptionHandler(LobbyNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyNotFoundException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = HttpLobbyResponseStatus.LOBBY_NOT_FOUND)
    )

    @ExceptionHandler(WrongLobbyPasswordException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleWrongLobbyPasswordException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = HttpLobbyResponseStatus.WRONG_PASSWORD)
    )

    @ExceptionHandler(UserInBlackListException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserInBlackListException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = HttpLobbyResponseStatus.USER_IN_LOBBY_BLACK_LIST)
    )

    @ExceptionHandler(LobbyInGameException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyInGameException(): ResponseEntity<JoinLobbyResponse> = ResponseEntity.ok(
        JoinLobbyResponse(responseStatus = HttpLobbyResponseStatus.LOBBY_IN_GAME)
    )
}