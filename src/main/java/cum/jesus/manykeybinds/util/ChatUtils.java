package cum.jesus.manykeybinds.util;

import cum.jesus.manykeybinds.ManyKeyBinds;
import net.minecraft.util.ChatComponentText;

public final class ChatUtils {
    public static void sendMessage(Object message, ChatColor... colors) {
        StringBuilder sb = new StringBuilder(colors.length * 2);

        for (ChatColor color : colors) {
            sb.append(color.format);
        }

        ManyKeyBinds.mc.thePlayer.addChatMessage(new ChatComponentText(sb.toString() + message));
    }

    public static void sendMessage(Object message) {
        ManyKeyBinds.mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(message)));
    }

    public static void sendPrefixMessage(Object message, ChatColor... colors) {
        StringBuilder sb = new StringBuilder(colors.length * 2);

        for (ChatColor color : colors) {
            sb.append(color.format);
        }

        ManyKeyBinds.mc.thePlayer.addChatMessage(new ChatComponentText("§8[§4ManyKeyBinds§8] " + sb + message));
    }

    public static void sendPrefixMessage(Object message) {
        sendPrefixMessage(message, ChatColor.RESET);
    }
}
