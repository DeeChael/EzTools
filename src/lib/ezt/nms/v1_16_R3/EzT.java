package lib.ezt.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.CommandDispatcher;
import net.minecraft.server.v1_16_R3.CommandListenerWrapper;
import net.minecraft.server.v1_16_R3.MinecraftServer;
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
            cmdMgr.set(ezT, new CommandManager_v1_16_R3(mojang));
            Field tfTol = ezTClass.getDeclaredField("transferTool");
            tfTol.setAccessible(true);
            tfTol.set(ezT, new TransferTool_v1_16_R3());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

}
