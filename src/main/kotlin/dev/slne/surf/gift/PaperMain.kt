package dev.slne.surf.gift

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.gift.commands.giftCommand
import dev.slne.surf.gift.services.GiftVisualizationService
import dev.slne.surf.gift.config.gift.GiftService
import dev.slne.surf.gift.config.location.LocationService
import dev.slne.surf.gift.db.DatabaseLoader
import dev.slne.surf.gift.listeners.PlayerInteractListener
import dev.slne.surf.gift.listeners.PlayerJoinListener
import dev.slne.surf.gift.listeners.PlayerQuitListener
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlinx.coroutines.delay
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

class PaperMain : SuspendingJavaPlugin() {

    override suspend fun onEnableAsync() {
        DatabaseLoader.connect(plugin.dataPath)
        DatabaseLoader.createTables()

        GiftService.registerGifts()
        LocationService.registerLocations()

        PlayerJoinListener.register()
        PlayerQuitListener.register()

        val packetEvents = PacketEvents.getAPI().eventManager

        packetEvents.registerListener(
            PlayerInteractListener,
            PacketListenerPriority.NORMAL
        )

        giftCommand()

        var lastCheckDate = LocalDate.now()
        plugin.launch {
            while (true){
                val currentDate = LocalDate.now()
                if (currentDate.isAfter(lastCheckDate)) {
                    lastCheckDate = currentDate

                    server.onlinePlayers.forEach { player ->
                        GiftVisualizationService.visualizeGiftForPlayer(player)
                    }
                }

                delay(1.seconds)
            }
        }
    }

    override fun onDisable() {
        DatabaseLoader.disconnect()
    }
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)
