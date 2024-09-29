package cum.jesus.manykeybinds.commands;

import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.util.ChatColor;
import cum.jesus.manykeybinds.util.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public final class RemoveActionCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "removeaction";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/removeaction (action id)";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            return;
        }

        String id = args[0];
        Action action = ManyKeyBinds.actionRegistry.findAction(id);

        if (action == null) {
            ChatUtils.sendPrefixMessage("No action exists with the id " + id, ChatColor.RED);
        }

        ManyKeyBinds.actionRegistry.unregisterAction(action);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, ManyKeyBinds.actionRegistry.getActionIDs());
        }

        return super.addTabCompletionOptions(sender, args, pos);
    }
}
