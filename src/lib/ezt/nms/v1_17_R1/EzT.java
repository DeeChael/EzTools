package lib.ezt.nms.v1_17_R1;

import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.server.MinecraftServer;
import org.eztools.utils.ReflectionAPI;

import java.lang.reflect.Field;

public class EzT {

    private final org.eztools.EzT ezT;

    public EzT(org.eztools.EzT plugin) {
        this.ezT = plugin;
    }

    public void load() {
        Class<org.eztools.EzT> ezTClass = org.eztools.EzT.class;
        CommandDispatcher commandDispatcher = MinecraftServer.getServer().getCommandDispatcher();
        Class<CommandDispatcher> commandDispatcherClass = CommandDispatcher.class;
        Field field = ReflectionAPI.getField(commandDispatcherClass, "g");
        assert field != null;
        field.setAccessible(true);
        try {
            Field cmdMgr = ezTClass.getDeclaredField("commandManager");
            com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> mojang = (com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper>) field.get(commandDispatcher);
            cmdMgr.setAccessible(true);
            cmdMgr.set(ezT, new CommandManager_v1_17_R1(mojang));
            Field tfTol = ezTClass.getDeclaredField("transferTool");
            tfTol.setAccessible(true);
            tfTol.set(ezT, new TransferTool_v1_17_R1());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

}
