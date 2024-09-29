package cum.jesus.manykeybinds.config;

import com.google.gson.*;
import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.action.ActionParsers;
import cum.jesus.manykeybinds.action.ActionRegistry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class ConfigManager {
    public static final File configDir = new File(ManyKeyBinds.mc.mcDataDir, "ManyKeyBinds");

    static {
        if (!configDir.exists()) configDir.mkdirs();
    }

    public static void save(ActionRegistry registry) throws IOException {
        File keybindsFile = new File(configDir, registry.getId() + ".json");

        if (!keybindsFile.exists() && !keybindsFile.createNewFile()) {
            throw new IOException("Failed to create keybinds file");
        }

        Files.write(keybindsFile.toPath(), toJsonObject(registry).toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @return a list of all complaints formed when loading
     */
    public static List<String> load(ActionRegistry registry) {
        File keybindsFile = new File(configDir, registry.getId() + ".json");
        List<String> complaints = new ArrayList<>();

        if (!keybindsFile.exists())  {
            complaints.add("Config file doesn't exist");
            return complaints;
        }

        try {
            JsonObject object = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(keybindsFile)));
            JsonObject metadata = null;

            if (object.has("metadata")) {
                JsonElement metadataElement = object.get("metadata");

                if (metadataElement instanceof JsonObject) {
                    metadata = (JsonObject) metadataElement;

                    if (metadata.has("version")) {
                        JsonElement versionElement = metadata.get("version");
                        if (!versionElement.isJsonPrimitive() || !versionElement.getAsJsonPrimitive().isString()) complaints.add("'version' string is not valid");
                    } else {
                        complaints.add("Metadata has no 'version'");
                    }

                    if (metadata.has("keybindCategory")) {
                        JsonElement keybindCategoryElement = metadata.get("keybindCategory");
                        if (!keybindCategoryElement.isJsonPrimitive() || !keybindCategoryElement.getAsJsonPrimitive().isString()) complaints.add("'keybindCategory' string is not valid");
                    } else {
                        complaints.add("Metadata has no 'keybindCategory'");
                    }
                } else {
                    complaints.add("'metadata' object is not valid");
                }
            } else {
                complaints.add("Config has no 'metadata'");
            }

            if (object.has("keybinds")) {
                JsonElement keybindsElement = object.get("keybinds");

                if (keybindsElement instanceof JsonArray) {
                    JsonArray keybinds = (JsonArray) keybindsElement;

                    int i = 0;
                    for (JsonElement keybindElement : keybinds) {
                        if (keybindElement instanceof JsonObject) {
                            JsonObject keybind = (JsonObject) keybindElement;

                            if (keybind.has("type")) {
                                JsonElement typeElement = keybind.get("type");

                                if (typeElement instanceof JsonPrimitive && ((JsonPrimitive) typeElement).isString()) {
                                    String type = typeElement.getAsString();
                                    Action action = ActionParsers.fromJsonObject(type, keybind, complaints);

                                    if (action != null) {
                                        if (!registry.hasID(action.getId())) {
                                            registry.registerAction(action);
                                        } else {
                                            complaints.add("Keybinds contains 2 or more duplicates of the id '" + action.getId() + "'");
                                        }
                                    } // the parser already adds complaints if null is returned
                                } else {
                                    complaints.add("'type' string is invalid");
                                }
                            } else {
                                complaints.add("Keybind has no 'type'");
                            }
                        } else {
                            complaints.add("Invalid keybind entry (" + i + ")");
                        }

                        i++;
                    }
                } else {
                    complaints.add("'keybinds' array is invalid");
                }
            } else {
                complaints.add("Config has no 'keybinds'");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return complaints;
    }

    private static JsonObject toJsonObject(ActionRegistry registry) {
        JsonObject object = new JsonObject();

        JsonObject metadata = new JsonObject();
        metadata.addProperty("version", ManyKeyBinds.VERSION);
        metadata.addProperty("keybindCategory", ManyKeyBinds.NAME);

        object.add("metadata", metadata);

        JsonArray keybinds = new JsonArray();
        for (Action action : registry.getActions()) {
            JsonObject actionObject = new JsonObject();
            action.save(actionObject);

            keybinds.add(actionObject);
        }

        object.add("keybinds", keybinds);

        return object;
    }
}
