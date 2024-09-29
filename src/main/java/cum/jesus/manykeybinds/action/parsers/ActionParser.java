package cum.jesus.manykeybinds.action.parsers;

import com.google.gson.JsonObject;
import cum.jesus.manykeybinds.action.Action;

import java.util.List;

public abstract class ActionParser {
    public abstract Action parse(String id, String[] args);

    public abstract Action fromJsonObject(String id, JsonObject object, List<String> complaints);
}
