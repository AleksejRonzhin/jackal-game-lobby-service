package ru.rsreu.jackal.api.lobby.http.connectionInfoGetting

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.exception.UserNotInLobbyException
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.responses.GetConnectionInfoResponse

@RestControllerAdvice(basePackageClasses = [LobbyConnectionInfoGettingController::class])
class LobbyConnectionInfoGettingControllerAdvice {
    @ExceptionHandler(UserNotInLobbyException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInAnyLobbyException(): ResponseEntity<GetConnectionInfoResponse> = ResponseEntity.ok(
        GetConnectionInfoResponse(responseStatus = HttpResponseStatus.USER_NOT_IN_LOBBY)
    )
}