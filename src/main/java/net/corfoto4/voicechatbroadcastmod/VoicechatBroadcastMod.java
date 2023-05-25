package net.corfoto4.voicechatbroadcastmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class VoicechatBroadcastMod implements ModInitializer {
	public static final String MOD_ID = "voicechatbroadcastmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftServer minecraftServer;

	@Override
	public void onInitialize() {
		// Get the minecraft server instance
		ServerLifecycleEvents.SERVER_STARTED.register(server -> VoicechatBroadcastMod.minecraftServer = server);

		// Provide log to say the mod is initialized
		LOGGER.info("Broadcast Mod Initialized");
	}
}
