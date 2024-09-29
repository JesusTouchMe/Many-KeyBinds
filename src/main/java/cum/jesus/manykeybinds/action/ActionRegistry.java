package cum.jesus.manykeybinds.action;

import cum.jesus.manykeybinds.config.ConfigManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class ActionRegistry {
    private final Set<Action> actions = new HashSet<>();
    private final String id;

    public ActionRegistry(String id) {
        this.id = id;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public String getId() {
        return id;
    }

    public Set<String> getActionIDs() {
        Set<String> res = new HashSet<>();

        for (Action action : actions) {
            res.add(action.getId());
        }

        return res;
    }

    public boolean hasID(String id) {
        for (Action action : actions) {
            if (action.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public void registerAction(Action action) {
        actions.add(action);
        action.register();

        try {
            ConfigManager.save(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unregisterAction(Action action) {
        actions.remove(action);
        action.unregister();

        try {
            ConfigManager.save(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Action findAction(String id) {
        for (Action action : actions) {
            if (action.getId().equals(id)) {
                return action;
            }
        }

        return null;
    }

    public void runAllActions() {
        for (Action action : actions) {
            if (action.getKeyBinding().isPressed()) {
                action.run();
            }
        }
    }
}
