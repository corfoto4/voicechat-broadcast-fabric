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

    public static VoicechatApi voicechatApi;

    @Override
    public String getPluginId() {
        return "voicechatbroadcastmod";
    }

    @Override
    public void initialize(VoicechatApi api) {
        voicechatApi = api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophone);
    }

    private void onMicrophone(MicrophonePacketEvent event) {

        // The connection might be null if the event is caused by other means
        if (event.getSenderConnection() == null) {
            return;
        }
        // Cast the generic player object of the voice chat API to an actual bukkit player
        // This object should always be a bukkit player object on bukkit based servers


        Group group = event.getSenderConnection().getGroup();

        // Check if the player sending the audio is actually in a group
        if (group == null) {
            return;
        }


        // Only broadcast the voice when the group name is "broadcast"
        if (!group.getName().strip().equalsIgnoreCase("broadcast")) {
            return;
        }

        // Cancel the actual microphone packet event that people in that group or close by don't hear the broadcaster twice
        event.cancel();

        VoicechatServerApi api = event.getVoicechat();


        MinecraftServer server = VoicechatBroadcastMod.minecraftServer;
        PlayerManager playerManager = server.getPlayerManager();
        List<ServerPlayerEntity> onlinePlayers = playerManager.getPlayerList();

        for (ServerPlayerEntity players : onlinePlayers)
        {
            if (players.getUuid().equals((event.getSenderConnection().getPlayer().getUuid())))
            {
                continue;
            }

            VoicechatConnection connection = api.getConnectionOf(players.getUuid());

            if (connection == null)
            {
                continue;
            }
            StaticSoundPacket convertedPacket;
            convertedPacket = createStaticSoundPacket(event.getPacket());
            api.sendStaticSoundPacketTo(connection, convertedPacket);
        }
    }

    public StaticSoundPacket createStaticSoundPacket(MicrophonePacket micPacket) {
        StaticSoundPacket soundPacket;
        soundPacket = micPacket.toStaticSoundPacket();

        return soundPacket;

        // Use the created static sound packet as needed
    }

}
