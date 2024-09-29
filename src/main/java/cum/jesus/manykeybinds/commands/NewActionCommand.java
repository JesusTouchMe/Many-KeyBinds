package cum.jesus.manykeybinds.commands;

import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import cum.jesus.manykeybinds.action.ActionParsers;
import cum.jesus.manykeybinds.util.ChatColor;
import cum.jesus.manykeybinds.util.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public final class NewActionCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "newaction";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/newaction (action type) (action id) [action params...]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            return;
        }

        if (ManyKeyBinds.actionRegistry.hasID(args[1])) {
            ChatUtils.sendPrefixMessage("There is already an action with the ID " + args[1], ChatColor.RED);
            return;
        }

        String[] realArgs = new String[args.length - 2];
        System.arraycopy(args, 2, realArgs, 0, realArgs.length);

        Action action = ActionParsers.parse(args[0], args[1], realArgs);

        if (action == null) {
            ChatUtils.sendPrefixMessage("Error occurred while creating action (TODO: BETTER ERROR LOGGING LOL)", ChatColor.RED);
            return;
        }

        ManyKeyBinds.actionRegistry.registerAction(action);

        ChatUtils.sendPrefixMessage("Action of type " + args[0] + " with the id " + args[1] + " was created successfully");
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, ActionParsers.getParserNames());
        }

        return super.addTabCompletionOptions(sender, args, pos);
    }
}
