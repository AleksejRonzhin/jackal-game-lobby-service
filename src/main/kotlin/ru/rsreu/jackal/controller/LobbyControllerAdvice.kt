package ru.rsreu.jackal.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.UserAlreadyInLobbyException
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.CreateLobbyStatus

@RestControllerAdvice
class LobbyControllerAdvice {

    @ExceptionHandler(UserAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserAlreadyInLobbyException(): ResponseEntity<CreateLobbyResponse> =
        ResponseEntity.ok(
            CreateLobbyResponse(responseStatus = CreateLobbyStatus.USER_ALREADY_IN_LOBBY)
        )
}