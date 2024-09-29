package cum.jesus.manykeybinds.listener;

import cum.jesus.manykeybinds.ManyKeyBinds;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public final class KeyInputListener {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        ManyKeyBinds.actionRegistry.runAllActions();
    }
}
