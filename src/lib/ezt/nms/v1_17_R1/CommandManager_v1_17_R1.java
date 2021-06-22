package lib.ezt.nms.v1_17_R1;

import com.mojang.brigadier.CommandDispatcher;
import lib.ezt.nms.v1_17_R1.commands.CommandEzT;
import lib.ezt.nms.v1_17_R1.commands.CommandEzTItem;
import net.minecraft.commands.CommandListenerWrapper;
import org.eztools.api.command.CommandManager;

public class CommandManager_v1_17_R1 implements CommandManager {

    private final CommandDispatcher<CommandListenerWrapper> commandDispatcher;

    public CommandManager_v1_17_R1(CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void register() {
        CommandEzT.register(this.commandDispatcher);
        CommandEzTItem.register(this.commandDispatcher);
    }

}
