package cum.jesus.manykeybinds.action.parsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.action.actions.ChatAction;

import java.util.List;

public final class ChatActionParser extends ActionParser {
    @Override
    public Action parse(String id, String[] args) {
        return new ChatAction(id, String.join(" ", args));
    }

    @Override
    public Action fromJsonObject(String id, JsonObject object, List<String> complaints) {
        if (object.has("message")) {
            JsonElement messageElement = object.get("message");

            if (messageElement instanceof JsonPrimitive && ((JsonPrimitive) messageElement).isString()) {
                String message = messageElement.getAsString();
                return new ChatAction(id, message);
            } else {
                complaints.add("'message' string is not valid");
            }
        } else {
            complaints.add("Keybind has no 'message'");
        }

        return null;
    }
}
