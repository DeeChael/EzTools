package lib.ezt.nms.v1_13_R2;

import net.minecraft.server.v1_13_R2.CommandDispatcher;
import net.minecraft.server.v1_13_R2.CommandListenerWrapper;
import net.minecraft.server.v1_13_R2.MinecraftServer;
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
            Field f = ezTClass.getDeclaredField("commandManager");
            com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> mojang = (com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper>) field.get(commandDispatcher);
            f.setAccessible(true);
            f.set(ezT, new CommandManager_v1_13_R2(mojang));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

}
