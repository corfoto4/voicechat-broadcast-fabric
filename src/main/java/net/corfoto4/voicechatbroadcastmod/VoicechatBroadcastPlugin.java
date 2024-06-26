package net.corfoto4.voicechatbroadcastmod;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.packets.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

import net.fabricmc.loader.api.FabricLoader;

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

    // Function to check if mod is running
    private boolean isModLoaded(String modName)
    {
        return FabricLoader.getInstance().isModLoaded(modName);
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

        // Get the minecraft server instance
        MinecraftServer server = VoicechatBroadcastMod.minecraftServer;

        // Get player talking
        // ServerPlayerEntity player = server.getPlayerManager().getPlayer(user.getUniqueId());
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(event.getSenderConnection().getPlayer().getUuid());

        // Run if luckperms exists
        if (isModLoaded("luckperms")) {

            // Get an instance of LuckPerms
            LuckPerms luckperms = LuckPermsProvider.get();

            // Get the current user sending the broadcast
            User lUser = luckperms.getUserManager().getUser(event.getSenderConnection().getPlayer().getUuid());

            // Check if the user collected exists
            if (lUser == null) {
                return;
            }

            // Check if the user has the broadcast permission. If not, tell them and quit broadcast.
            if (!(lUser.getCachedData().getPermissionData().checkPermission("voicechat.broadcast").asBoolean())) {
                // If not, send them an action bar message saying that they do not have the permission
                Text message = Text.of("You Cannot Broadcast to This Server");
                Text formattedText = message.copy().formatted(Formatting.RED); // Format the text red

                // If the player is a player, send the message
                if (!(player == null)) {
                    player.sendMessage(formattedText, true);
                }

                return;
            }
        }

        // Cancel the actual microphone packet event that people in that group or close by don't hear the broadcaster twice
        // Event cancel come before permissions so that if someone doesn't have the permission, they won't be able to talk in the broadcast group
        event.cancel();

        // Send action bar notification saying that the player is broadcasting
        Text message = Text.of("You are Broadcasting to the Server!");
        Text formattedText = message.copy().formatted(Formatting.WHITE); // Format the text white

        // If the player is a player, send the message
        if (!(player == null)) {
            player.sendMessage(formattedText, true);
        }

        // Get events from the voice chat
        VoicechatServerApi api = event.getVoicechat();

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

        // Build the static sound packet from microphone packet
        soundPacket = micPacket.staticSoundPacketBuilder().build();

        return soundPacket;
    }

}
