package info.preva1l.fadps.levels.editor.commands;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.editor.commands.sub.EditorDebugCommand;
import info.preva1l.fadps.user.CommandUser;
import info.preva1l.fadps.utils.commands.BasicCommand;
import info.preva1l.fadps.utils.commands.Command;

public class EditorCommand extends BasicCommand {
    @Command(name = "editor")
    public EditorCommand(Fadps plugin) {
        super(plugin);
        getSubCommands().add(new EditorDebugCommand(plugin));
    }

    @Override
    public void execute(CommandUser sender, String[] args) {
        if (args.length >= 1) {
            if (subCommandExecutor(sender, args)) return;
            sender.sendMessage(plugin.getLang().getCommands().getDoesNotExist());
            return;
        }

        sender.sendMessage("Editor Help Menu");
    }
}
