package cum.jesus.manykeybinds.action.actions;

import com.google.gson.JsonObject;
import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.util.ChatUtils;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public final class ChatAction extends Action {
    private String msg;

    public ChatAction(String id, String msg) {
        super(new KeyBinding(id, Keyboard.KEY_NONE, ManyKeyBinds.NAME), id);

        this.msg = msg;
    }

    @Override
    public String getType() {
        return "chat";
    }

    @Override
    public void print() {
        ChatUtils.sendMessage(id + " - Chat \"" + msg + '"');
    }

    @Override
    public void run() {
        ManyKeyBinds.mc.thePlayer.sendChatMessage(msg);
    }

    @Override
    protected void saveInternal(JsonObject object) {
        object.addProperty("message", msg);
    }
}
