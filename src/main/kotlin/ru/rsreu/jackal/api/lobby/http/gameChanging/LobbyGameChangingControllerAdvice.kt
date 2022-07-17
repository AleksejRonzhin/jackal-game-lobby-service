package ru.rsreu.jackal.api.lobby.http.gameChanging

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.LobbyInGameException
import ru.rsreu.jackal.exception.UserNotInLobbyException
import ru.rsreu.jackal.exception.UserNotIsHostException
import ru.rsreu.jackal.shared_models.responses.ChangeGameResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestControllerAdvice(basePackageClasses = [LobbyGameChangingController::class])
class LobbyGameChangingControllerAdvice {
    @ExceptionHandler(UserNotIsHostException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserIsNotHostException(): ResponseEntity<ChangeGameResponse> = ResponseEntity.ok(
        ChangeGameResponse(responseStatus = HttpLobbyResponseStatus.USER_NOT_LOBBY_HOST)
    )

    @ExceptionHandler(UserNotInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInLobbyException(): ResponseEntity<ChangeGameResponse> = ResponseEntity.ok(
        ChangeGameResponse(responseStatus = HttpLobbyResponseStatus.USER_NOT_IN_LOBBY)
    )

    @ExceptionHandler(LobbyInGameException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyInGameException(): ResponseEntity<ChangeGameResponse> = ResponseEntity.ok(
        ChangeGameResponse(responseStatus = HttpLobbyResponseStatus.LOBBY_IN_GAME)
    )
}
