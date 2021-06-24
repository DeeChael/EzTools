package lib.ezt.nms.v1_17_R1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.ChatMessage;
import org.eztools.EzT;

public class CommandEzT {

    public static void register(CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        LiteralCommandNode<CommandListenerWrapper> literalCommandNode = commandDispatcher.register(
                net.minecraft.commands.CommandDispatcher
                        .a("ezt")
                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt"))
                        .then(
                                net.minecraft.commands.CommandDispatcher
                                        .a("reload")
                                        .requires(requirement -> requirement.hasPermission(4, "ezt.command.ezt.reload"))
                                        .executes((commandContext -> executeReload(commandContext.getSource())))
                        )
                        .then(
                                net.minecraft.commands.CommandDispatcher
                                        .a("help")
                                        .requires(requirement -> requirement.hasPermission(4, "ezt.command.ezt.help"))
                                        .executes((commandContext -> executeHelp(commandContext.getSource())))
                        )
                        .then(
                                net.minecraft.commands.CommandDispatcher
                                        .a("info")
                                        .requires(requirement -> requirement.hasPermission(4, "ezt.command.ezt.info"))
                                        .executes((commandContext -> executeInfo(commandContext.getSource())))
                        )
                        .then(
                                net.minecraft.commands.CommandDispatcher
                                        .a("source-code")
                                        .requires(requirement -> requirement.hasPermission(4, "ezt.command.ezt.sc"))
                                        .executes((commandContext -> executeSourceCode(commandContext.getSource())))
                        )
        );
        commandDispatcher.register(net.minecraft.commands.CommandDispatcher.a("eztools").redirect(literalCommandNode));
        commandDispatcher.register(net.minecraft.commands.CommandDispatcher.a("eztool").redirect(literalCommandNode));
    }

    private static int executeReload(CommandListenerWrapper commandListenerWrapper) {
        int i = 0;
        if (EzT.reload()) {
            commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt.reload.success")), false);
            i++;
        }
        return i;
    }

    private static int executeHelp(CommandListenerWrapper commandListenerWrapper) {
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt.help.message")), false);
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
        return 1;
    }

    private static int executeInfo(CommandListenerWrapper commandListenerWrapper) {
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt.info.message")), false);
        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
        return 1;
    }

    private static int executeSourceCode(CommandListenerWrapper commandListenerWrapper) {
        commandListenerWrapper.sendMessage(new ChatMessage("https://github.com/DeeChael/EzTools"), false);
        return 1;
    }

}
