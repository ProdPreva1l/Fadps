package info.preva1l.fadps.utils.commands;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.user.CommandUser;
import info.preva1l.fadps.user.ConsoleUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BasicCommand {
    public Fadps plugin;
    private final Command info;
    private final List<BasicSubCommand> subCommands;

    public BasicCommand(Fadps plugin) {
        this.plugin = plugin;
        this.info = Arrays.stream(this.getClass().getConstructors()).filter(method -> method.getAnnotation(Command.class) != null).map(method -> method.getAnnotation(Command.class)).findFirst().orElse(null);
        this.subCommands = new ArrayList<>();

        if (info == null) {
            throw new RuntimeException("%s constructor must be annotated with @Command"
                    .formatted(this.getClass().getSimpleName()));
        }
    }

    public abstract void execute(CommandUser sender, String[] args);

    public List<String> tabComplete(CommandUser sender, String[] args) {
        return new ArrayList<>();
    }

    /**
     * Handles subcommand execution easily.
     * @param sender command sender
     * @param args command args
     * @return true if the subcommand was executed,
     * false if the command does not exist
     */
    public boolean subCommandExecutor(CommandUser sender, String[] args) {
        for (BasicSubCommand subCommand : subCommands) {
            if (args[0].equalsIgnoreCase(subCommand.getInfo().name())
                    || Arrays.stream(subCommand.getInfo().aliases()).toList().contains(args[0])) {

                if (subCommand.getInfo().inGameOnly() && sender instanceof ConsoleUser) {
                    sender.sendMessage(plugin.getLang().getCommands().getMustBePlayer());
                    return true;
                }

                if (!subCommand.getInfo().permission().isEmpty() && !sender.hasPermission(subCommand.getInfo().permission())) {
                    sender.sendMessage(plugin.getLang().getCommands().getNoPermission());
                    return true;
                }

                if (subCommand.getInfo().async()) {
                    plugin.executeAsync(() -> subCommand.execute(sender, removeFirstElement(args)));
                } else {
                    plugin.executeSync(() -> subCommand.execute(sender, removeFirstElement(args)));
                }
                return true;
            }
        }
        return false;
    }

    private String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }
}