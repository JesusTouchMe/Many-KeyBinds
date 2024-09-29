package cum.jesus.manykeybinds.commands;

import cum.jesus.manykeybinds.ManyKeyBinds;
import cum.jesus.manykeybinds.action.Action;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public final class ActionsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "actions";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/actions";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        for (Action action : ManyKeyBinds.actionRegistry.getActions()) {
            action.print();
        }
    }
}
