package net.corfoto4.voicechatbroadcastmod;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;

import de.maxhenkel.voicechat.api.packets.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.PlayerManager;


import java.util.List;

public class VoicechatBroadcastPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return "voicechatbroadcastmod";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophone);
    }

    private void onMicrophone(MicrophonePacketEvent event) {

        // Check if the event is from a sending player
        if (event.getSenderConnection() == null) {
            return;
        }

        // Get the current group of the sending player
        Group group = event.getSenderConnection().getGroup();

        // Check if the player sending the audio is actually in a group
        if (group == null) {
            return;
        }

        // Make sure that the player is in the Broadcast group
        if (!group.getName().strip().equalsIgnoreCase("broadcast")) {
            return;
        }

        // Cancel the actual microphone packet event that people in that group or close by don't hear the broadcaster twice
        event.cancel();

        // Get events from the voice chat
        VoicechatServerApi api = event.getVoicechat();

        // Get the minecraft server instance
        MinecraftServer server = VoicechatBroadcastMod.minecraftServer;

        // Get a list of players online in the server
        List<ServerPlayerEntity> onlinePlayers = server.getPlayerManager().getPlayerList();

        // Repeat for every player currently on the server
        for (ServerPlayerEntity players : onlinePlayers)
        {
            // Cancel packet processing if it in relation to the broadcaster speaking
            if (players.getUuid().equals((event.getSenderConnection().getPlayer().getUuid())))
            {
                continue;
            }

            // Get the voicechat connection of the player
            VoicechatConnection connection = api.getConnectionOf(players.getUuid());

            // Cancel the packet if there is no connection for the player
            if (connection == null)
            {
                continue;
            }

            // Create static sound packet
            StaticSoundPacket convertedPacket;
            convertedPacket = createStaticSoundPacket(event.getPacket());

            // Send the static sound packet out to the connection
            api.sendStaticSoundPacketTo(connection, convertedPacket);
        }
    }

    public StaticSoundPacket createStaticSoundPacket(MicrophonePacket micPacket) {
        // Convert sound packet to static sound packet

        StaticSoundPacket soundPacket;

        // Build the static sound packet from micophone packet
        soundPacket = micPacket.toStaticSoundPacket();

        return soundPacket;
    }

}
