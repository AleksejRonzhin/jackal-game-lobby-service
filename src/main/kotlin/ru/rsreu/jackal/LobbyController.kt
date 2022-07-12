package ru.rsreu.jackal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.models.Lobby

@RestController
class LobbyController(private val service: LobbyService) {

    @GetMapping("/test")
    fun test(): Collection<Lobby> {
        return service.test()
    }
}