package ru.rsreu.jackal.websocket.leaving.dto

import ru.rsreu.jackal.websocket.WebSocketResponseType

data class UserLeavedInfoForOneResponse(val type: WebSocketResponseType = WebSocketResponseType.LEAVED_INFO_FOR_ONE)