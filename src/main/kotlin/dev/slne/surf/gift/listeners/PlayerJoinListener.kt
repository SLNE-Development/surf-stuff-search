package dev.slne.surf.gift.listeners

import dev.slne.surf.gift.services.GiftVisualizationService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        GiftVisualizationService.visualizeGiftForPlayer(player)
    }
}