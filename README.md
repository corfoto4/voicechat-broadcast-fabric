# Voice Chat Broadcast Plugin - Fabric

This fabric mod/plugin implementation is based on [henkelmax's Bukkit Voice Chat Broadcast Plugin](https://github.com/henkelmax/voicechat-broadcast-plugin).

This mod is currently in a beta stage with slow updates.

<hr>

## Usage

- Have a Fabric server running [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat).
- Create and join a group called `broadcast` (Case Insensitive).
- Talk in the group.
- Your voice will be broadcasted to everyone on the server using the voice chat mod.

## Capabilities

When used **<ins>without</ins>** luckperms, **anyone** in the `broadcast` group can broadcast to the server.

When used with luckperms, the permission node `voicechat.broadcast` can be used.
If the node is set to `true` it will allow the player to broadcast. If set to `false`, the player cannot broadcast.
Defaults to `false`.

If you wish to use the permissions capability, you must install [LuckPerms Fabric](https://luckperms.net/download).

## Dependencies

### 1.20.6:

- Simple Voice Chat Version: 1.20.6-2.5.16 or greater.

### 1.19.4 (beta):

- Simple Voice Chat Version: 1.19.4-2.3.28 or greater.



