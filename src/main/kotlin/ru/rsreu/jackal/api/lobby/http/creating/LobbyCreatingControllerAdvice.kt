package ru.rsreu.jackal.api.lobby.http.creating

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.NotUniqueLobbyTitleException
import ru.rsreu.jackal.exception.UserAlreadyInLobbyException
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestControllerAdvice(basePackageClasses = [LobbyCreatingController::class])
class LobbyCreatingControllerAdvice {
    @ExceptionHandler(UserAlreadyInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleHostAlreadyInLobbyException(): ResponseEntity<CreateLobbyResponse> = ResponseEntity.ok(
        CreateLobbyResponse(responseStatus = HttpLobbyResponseStatus.USER_ALREADY_IN_LOBBY)
    )

    @ExceptionHandler(NotUniqueLobbyTitleException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleNotUniqueLobbyTitleException(): ResponseEntity<CreateLobbyResponse> = ResponseEntity.ok(
        CreateLobbyResponse(responseStatus = HttpLobbyResponseStatus.NOT_UNIQUE_LOBBY_TITLE)
    )
}