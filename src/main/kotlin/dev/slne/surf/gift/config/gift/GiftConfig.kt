package dev.slne.surf.gift.config.gift

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class GiftConfig(
    val gifts: MutableList<Gift>
)