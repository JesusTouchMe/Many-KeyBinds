package cum.jesus.manykeybinds.action.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.util.ChatUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import org.lwjgl.input.Keyboard;

import static net.minecraft.util.EnumChatFormatting.RED;

public final class CommandAction extends Action {
    private String command;
    private String[] args;

    public CommandAction(String id, String command, String[] args) {
        super(new KeyBinding(id, Keyboard.KEY_NONE, ManyKeyBinds.NAME), id);

        this.command = command;
        this.args = args;
    }

    @Override
    public String getType() {
        return "command";
    }

    @Override
    public void print() {
        ChatUtils.sendMessage(id + " - Command \"/" + command + " " + String.join(" ", args) + "\"");
    }

    /**
     * Basic copy of {@link ClientCommandHandler#executeCommand(ICommandSender, String)}
     */
    @Override
    public void run() {
        ICommand icommand = ClientCommandHandler.instance.getCommands().get(command);

        if (icommand == null) {
            return;
        }

        ICommandSender sender = ManyKeyBinds.mc.thePlayer;

        try {
            CommandEvent event = new CommandEvent(icommand, sender, args);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                if (event.exception != null)
                {
                    throw event.exception;
                }
                return;
            }

            icommand.processCommand(sender, args);
        } catch (WrongUsageException wue) {
            sender.addChatMessage(format(RED, "commands.generic.usage", format(RED, wue.getMessage(), wue.getErrorObjects())));
        }
        catch (CommandException ce)
        {
            sender.addChatMessage(format(RED, ce.getMessage(), ce.getErrorObjects()));
        }
        catch (Throwable t)
        {
            sender.addChatMessage(format(RED, "commands.generic.exception"));
            t.printStackTrace();
        }
    }

    @Override
    protected void saveInternal(JsonObject object) {
        object.addProperty("command", command);

        JsonArray args = new JsonArray();
        for (String arg : this.args) {
            args.add(new JsonPrimitive(arg));
        }

        object.add("args", args);
    }

    private static ChatComponentTranslation format(EnumChatFormatting color, String str, Object... args) {
        ChatComponentTranslation ret = new ChatComponentTranslation(str, args);
        ret.getChatStyle().setColor(color);
        return ret;
    }
}
