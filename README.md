# Voice Chat Broadcast Plugin - Fabric

This fabric mod/plugin is based on [henkelmax's Bukkit Voice Chat Broadcast Plugin](https://github.com/henkelmax/voicechat-broadcast-plugin).

This mod has slow updates.

<hr>

## Usage

- Have a Fabric server running [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat) and Fabric API.
- Create and join a group called `broadcast` (Case Insensitive).
- (Optional) If using luckperms, ensure permission node `voicechat.broadcast` is set to `true`.
- Talk in the group.
- Your voice will be broadcasted to everyone on the server using the voicechat mod.

## Capabilities

When used **<ins>without</ins>** luckperms, **anyone** in the `broadcast` group can broadcast to the server.

When used with luckperms, the permission node `voicechat.broadcast` can be used.
If the node is set to `true` it will allow the player to broadcast. If set to `false`, the player cannot broadcast.
Defaults to `false`.

If you wish to use the permissions capability, you must install [LuckPerms Fabric](https://luckperms.net/download).

## Dependencies

- Fabric API
- Simple Voice Chat version: `1.x.x-2.5.x` (e.g. `1.20.6-2.5.12`)


