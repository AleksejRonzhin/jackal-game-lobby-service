package ru.rsreu.jackal.websocket.dto

data class LeavingErrorResponse(
    val type: WebSocketResponseType = WebSocketResponseType.LEAVING_ERROR,
    val leaveErrorType: LeaveErrorType
)

enum class LeaveErrorType {
    NOT_IN_LOBBY, USER_IN_GAME
}
