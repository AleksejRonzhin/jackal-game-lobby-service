package ru.rsreu.jackal.api.lobby.websocket.leaving.dto

import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessage
import ru.rsreu.jackal.api.lobby.websocket.WebSocketMessageType

class UserLeavedInfoForOneResponse : WebSocketMessage(WebSocketMessageType.LEAVED_INFO_FOR_ONE)