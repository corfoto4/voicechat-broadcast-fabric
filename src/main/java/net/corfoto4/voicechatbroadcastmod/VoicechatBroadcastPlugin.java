package net.corfoto4.voicechatbroadcastmod;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;

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
        if (!(event.getSenderConnection().getPlayer().getPlayer() instanceof Player player)) {
            return;
        }

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
    }

}
