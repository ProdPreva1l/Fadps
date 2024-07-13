package info.preva1l.fadps.levels.editor.commands.sub;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.editor.EditorTask;
import info.preva1l.fadps.user.CommandUser;
import info.preva1l.fadps.user.OnlineUser;
import info.preva1l.fadps.utils.commands.BasicSubCommand;
import info.preva1l.fadps.utils.commands.Command;

public class EditorDebugCommand extends BasicSubCommand {
    @Command(name = "debug", permission = "fadps.admin")
    public EditorDebugCommand(Fadps plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandUser sender, String[] args) {
        new EditorTask(((OnlineUser) sender).getAudience()).runTaskTimer(plugin, 0L, 10L);
    }
}
