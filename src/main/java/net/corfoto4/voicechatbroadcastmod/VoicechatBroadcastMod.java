package net.corfoto4.voicechatbroadcastmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class VoicechatBroadcastMod implements ModInitializer {
	public static final String MOD_ID = "voicechatbroadcastmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftServer minecraftServer;

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			// Server started event, you can now access the server instance
			MinecraftServer minecraftServer = server.getCommandSource().getServer();

			// Do something with the Minecraft server instance
		});

		LOGGER.info("Initialised Broadcast Mod");
	}
}
