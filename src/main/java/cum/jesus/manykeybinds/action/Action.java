package cum.jesus.manykeybinds.action;

import com.google.gson.JsonObject;
import cum.jesus.manykeybinds.ManyKeyBinds;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.lang3.ArrayUtils;

public abstract class Action {
    protected final KeyBinding keyBinding;
    protected final String id;

    protected Action(KeyBinding keyBinding, String id) {
        this.keyBinding = keyBinding;
        this.id = id;
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public String getId() {
        return id;
    }

    public void register() {
        ClientRegistry.registerKeyBinding(keyBinding);
    }

    public void unregister() {
        ManyKeyBinds.mc.gameSettings.keyBindings = ArrayUtils.removeElement(ManyKeyBinds.mc.gameSettings.keyBindings, keyBinding);
    }

    public final void save(JsonObject object) {
        object.addProperty("type", getType());
        object.addProperty("id", getId());

        saveInternal(object);
    }

    public abstract String getType();

    public abstract void print(); // print in chat for actions command

    public abstract void run(); // called by KeyInputListener

    protected abstract void saveInternal(JsonObject object);
}
