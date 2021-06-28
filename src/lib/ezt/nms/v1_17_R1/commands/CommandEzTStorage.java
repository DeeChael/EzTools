package lib.ezt.nms.v1_17_R1.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.server.level.EntityPlayer;

import java.util.Collection;

public final class CommandEzTStorage {

    public static void register(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        LiteralArgumentBuilder<CommandListenerWrapper> command = CommandDispatcher
                .a("ezt-storage")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-storage"));
        command.then(CommandDispatcher
                .a("item")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-storage.item"))
                .then(CommandDispatcher
                        .a("list")
                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-storage.item.list"))
                        .executes((commandContext) -> executeItemList(commandContext.getSource()))
                )
        );
        commandDispatcher.register(command);
    }

    private static int executeItemList(CommandListenerWrapper commandListenerWrapper) {
        int i = 0;

        return i;
    }

    private static int executeItemSave(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, String name) {
        int i = 0;

        return i;
    }

    private static int executeItemRemove(CommandListenerWrapper commandListenerWrapper, String name) {
        int i = 0;
        return i;
    }

    private static int executeItemGive(CommandListenerWrapper commandListenerWrapper, Collection<EntityPlayer> entityPlayers, String name) {
        int i = 0;
        return i;
    }

}
