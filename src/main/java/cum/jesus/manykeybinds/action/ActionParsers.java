package cum.jesus.manykeybinds.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cum.jesus.manykeybinds.action.parsers.ActionParser;
import cum.jesus.manykeybinds.action.parsers.ChatActionParser;
import cum.jesus.manykeybinds.action.parsers.CommandActionParser;
import scala.annotation.meta.field;

import java.util.*;

public final class ActionParsers {
    private static final Map<String, ActionParser> parsers = new HashMap<>();

    static {
        parsers.put("chat", new ChatActionParser());
        parsers.put("command", new CommandActionParser());
    }

    public static Collection<String> getParserNames() {
        return parsers.keySet();
    }

    public static Action parse(String parserType, String actionId, String[] args) {
        ActionParser parser = parsers.get(parserType);
        if (parser == null) {
            return null;
        }

        return parser.parse(actionId, args);
    }

    public static Action fromJsonObject(String parserType, JsonObject object, List<String> complaints) {
        ActionParser parser = parsers.get(parserType);
        if (parser == null) {
            complaints.add("No parser for action type " + parserType);
            return null;
        }

        if (object.has("id")) {
            JsonElement idElement = object.get("id");

            if (idElement instanceof JsonPrimitive && ((JsonPrimitive) idElement).isString()) {
                String id = idElement.getAsString();
                return parser.fromJsonObject(id, object, complaints);
            } else {
                complaints.add("'id' string is invalid");
            }
        } else {
            complaints.add("Keybind has no 'id'");
        }

        return null;
    }
}
