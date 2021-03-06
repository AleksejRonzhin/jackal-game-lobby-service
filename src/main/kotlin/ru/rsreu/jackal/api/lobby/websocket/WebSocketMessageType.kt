package ru.rsreu.jackal.api.lobby.websocket

enum class WebSocketMessageType {
    CONNECTED_INFO_FOR_ALL,
    CONNECTED_INFO_FOR_ONE,
    CONNECTION_ERROR,
    LEAVED_INFO_FOR_ALL,
    LEAVED_INFO_FOR_ONE,
    LEAVING_ERROR,
    CHANGED_STATE_INFO_FOR_ALL,
    CHANGING_STATE_ERROR,
    KICKED_INFO_FOR_ALL,
    KICKED_INFO_FOR_ONE,
    KICKING_ERROR,
    CHECK_CONNECTION_REQUEST,
    LOBBY_NOT_READY_INFO_FOR_ALL,
    GAME_SESSION_CREATION_INFO_FOR_ALL,
    GAME_SESSION_CREATION_ERROR_INFO_FOR_ALL,
    REJECTED_GAME_INFO_FOR_ALL
}