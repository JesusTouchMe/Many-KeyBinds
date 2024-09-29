package cum.jesus.manykeybinds.action.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.util.ChatUtils;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

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

    @Override
    public void run() {
        ManyKeyBinds.mc.thePlayer.sendChatMessage("/" + command + " " + String.join(" ", args));
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
}
