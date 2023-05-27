# Voice Chat Broadcast Plugin - Fabric

This fabric mod/plugin is based on [Max Henkel's Spigot Voice Chat Broadcast Plugin](https://github.com/henkelmax/voicechat-broadcast-plugin).
<hr>

## Usage
- Have a Fabric server running [Simple Voice Chat](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat) 1.19.4-2.2.28 or later.
- Install [this plugin](https://github.com/corfoto4/Fabric-VoicechatBroadcastMod-1.19/releases).
- Create and join a group called `broadcast` (Case Insensitive).
- Talk in the group.
- Your voice chould be broadcasted to everyone on the server using the voice chat mod.

## Capabilities
This plugin can be used with LuckPerms with the permission node: `voicechat.broadcast`.
If the node is set to `true`, then it will allow the player to send a broadcast message. If set to `false`,
then the player cannot broadcast (default `false`).

## Compatibility

This plugin is currently in a pre-release stage. It is currently only designed for Simple Voice Chat Version 1.19.4-2.2.28 or greater.

The mod is designed to run on Minecraft Fabric 1.19.4 with the following API versions:
- Fabric Loader: 0.14.19
- Fabric API Version: 0.82.0+1.19.4
- Simple Voice Chat API Version: 2.3.3
- LuckPerms API Version: 5.4

If you wish to use the permissions capability, you must install [LuckPerms Fabric](https://luckperms.net/download).
