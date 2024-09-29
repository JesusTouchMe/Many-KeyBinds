package cum.jesus.manykeybinds;

import com.google.gson.Gson;
import cum.jesus.manykeybinds.action.ActionRegistry;
import cum.jesus.manykeybinds.commands.ActionsCommand;
import cum.jesus.manykeybinds.commands.NewActionCommand;
import cum.jesus.manykeybinds.commands.RemoveActionCommand;
import cum.jesus.manykeybinds.config.ConfigManager;
import cum.jesus.manykeybinds.listener.KeyInputListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ManyKeyBinds.MODID, name = ManyKeyBinds.NAME, version = ManyKeyBinds.VERSION)
public final class ManyKeyBinds {
    public static final String MODID = "manykeybinds";
    public static final String NAME = "Many Keybinds";
    public static final String VERSION = "%VERSION%";

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static final ActionRegistry actionRegistry = new ActionRegistry("keybinds");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigManager.load(actionRegistry);

        ClientCommandHandler.instance.registerCommand(new ActionsCommand());
        ClientCommandHandler.instance.registerCommand(new NewActionCommand());
        ClientCommandHandler.instance.registerCommand(new RemoveActionCommand());

        MinecraftForge.EVENT_BUS.register(new KeyInputListener());
    }
}
