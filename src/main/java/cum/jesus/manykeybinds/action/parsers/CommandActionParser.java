package cum.jesus.manykeybinds.action.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.action.actions.CommandAction;

import java.util.List;

public final class CommandActionParser extends ActionParser {
    @Override
    public Action parse(String id, String[] args) {
        if (args.length == 0) {
            return null;
        }

        String command = args[0];
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        return new CommandAction(id, command, newArgs);
    }

    @Override
    public Action fromJsonObject(String id, JsonObject object, List<String> complaints) {
        String command = null;
        String[] args = null;

        if (object.has("command")) {
            JsonElement commandElement = object.get("command");

            if (commandElement instanceof JsonPrimitive && ((JsonPrimitive) commandElement).isString()) {
                command = commandElement.getAsString();
            } else {
                complaints.add("'command' string is not valid");
                return null;
            }
        } else {
            complaints.add("Keybind has no 'command'");
            return null;
        }

        if (object.has("args")) {
            JsonElement argsElement = object.get("args");

            if (argsElement instanceof JsonArray) {
                JsonArray argsArray = (JsonArray) argsElement;
                args = new String[argsArray.size()];

                int i = 0;
                for (JsonElement element : argsArray) {
                    if (element instanceof JsonPrimitive && ((JsonPrimitive) element).isString()) {
                        args[i] = element.getAsString();
                    } else {
                        complaints.add("'args'[" + i + "] string is not valid");
                        return null;
                    }

                    i++;
                }
            } else {
                complaints.add("'args' array is not valid");
                return null;
            }
        } else {
            complaints.add("Keybind has no 'args'");
            return null;
        }

        return new CommandAction(id, command, args);
    }
}
