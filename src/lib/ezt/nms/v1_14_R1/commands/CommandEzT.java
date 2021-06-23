package lib.ezt.nms.v1_14_R1.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.v1_14_R1.ChatMessage;
import net.minecraft.server.v1_14_R1.CommandDispatcher;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
import org.eztools.EzT;

public class CommandEzT {

    public static void register( com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        LiteralCommandNode<CommandListenerWrapper> literalCommandNode = commandDispatcher.register(
                CommandDispatcher
                        .a("ezt")
                        .requires((requirement) -> {
                            return requirement.hasPermission(4, "ezt.command.ezt");
                        })
                        .then(
                                CommandDispatcher.a("subcmd", StringArgumentType.string())
                                        .suggests((commandContext, suggestionsBuilder) -> suggestionsBuilder.suggest("help").suggest("info").suggest("source-code").buildFuture())
                                        .executes((commandContext -> execute(commandContext.getSource(), StringArgumentType.getString(commandContext, "subcmd"))))
                        )
        );
        commandDispatcher.register(CommandDispatcher.a("eztools").redirect(literalCommandNode));
        commandDispatcher.register(CommandDispatcher.a("eztool").redirect(literalCommandNode));
    }

    private static int execute(CommandListenerWrapper commandListenerWrapper, String type) {
        int i = 0;
        if (type != null) {
            switch (type) {
                case "help" -> {
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt.help.message")), false);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
                    i++;
                }
                case "info" -> {
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt.info.message")), false);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.line")), false);
                    i++;
                }
                case "source-code" -> {
                    commandListenerWrapper.sendMessage(new ChatMessage("https://github.com/DeeChael/EzTools"), false);
                    i++;
                }
            }
        }
        return i;
    }

}
