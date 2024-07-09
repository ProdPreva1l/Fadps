package info.preva1l.fadps.utils.commands;

import com.sk89q.worldedit.extension.platform.Platform;
import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.user.CommandUser;
import info.preva1l.fadps.user.ConsoleUser;
import info.preva1l.fadps.user.OnlineUser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class CommandManager {
    protected final Fadps plugin;
    protected final List<BasicCommand> loadedCommands;

    private final Field COMMAND_MAP_FIELD;

    protected CommandManager(Fadps plugin) {
        this.plugin = plugin;
        this.loadedCommands = new ArrayList<>();
        try {
            COMMAND_MAP_FIELD = SimplePluginManager.class.getDeclaredField("commandMap");
            COMMAND_MAP_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Register a new command.
     * @param platform the platform that the commands code will run on.
     * @param basicCommand the command.
     */
    public void registerCommand(Platform platform, BasicCommand basicCommand) {
        getCommandMap(Bukkit.getServer()).register("fadps", new CommandExecutor(basicCommand));
        loadedCommands.add(basicCommand);
        plugin.getLogger().info(String.format("Registered Command %s", basicCommand.getInfo().name()));
    }

    public class CommandExecutor extends BukkitCommand {
        private final BasicCommand basicCommand;

        public CommandExecutor(BasicCommand basicCommand) {
            super(basicCommand.getInfo().name());
            this.setAliases(Arrays.asList(basicCommand.getInfo().aliases()));
            if (!basicCommand.getInfo().permission().isEmpty()) {
                this.setPermission(basicCommand.getInfo().permission());
            }
            this.basicCommand = basicCommand;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            if (this.basicCommand.getInfo().inGameOnly() && sender instanceof ConsoleCommandSender) {
                sender.sendMessage(plugin.getLang().getCommands().getMustBePlayer());
                return false;
            }
            if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
                sender.sendMessage(plugin.getLang().getCommands().getNoPermission());
                return false;
            }

            CommandUser commandUser = sender instanceof Player
                    ? OnlineUser.wrap((Player) sender)
                    : ConsoleUser.wrap(sender);

            if (this.basicCommand.getInfo().async()) {
                plugin.executeAsync(() -> basicCommand.execute(commandUser, args));
            } else {
                plugin.executeSync(() -> basicCommand.execute(commandUser, args));
            }
            return false;
        }

        @NotNull
        @Override
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
            CommandUser commandUser = sender instanceof Player
                    ? OnlineUser.wrap((Player) sender)
                    : ConsoleUser.wrap(sender);

            // Primary argument
            if (args.length <= 1) {
                List<String> completors = basicCommand.tabComplete(commandUser, args);

                if (completors.isEmpty() && !basicCommand.getSubCommands().isEmpty()) {
                    List<String> ret = new ArrayList<>();
                    for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                        if (!subCommand.getInfo().permission().isEmpty() && !commandUser.hasPermission(subCommand.getInfo().permission())) {
                            continue;
                        }
                        ret.add(subCommand.getInfo().name());
                        Collections.addAll(ret, subCommand.getInfo().aliases());
                    }
                    if (args.length == 0) {
                        completors.addAll(ret);
                    } else {
                        StringUtil.copyPartialMatches(args[0], ret, completors);
                    }
                    return completors;
                }

                return completors;
            }

            // Sub command tab completer
            List<String> completors = new ArrayList<>();

            List<String> ret = new ArrayList<>();
            for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                if (!subCommand.getInfo().name().equals(args[0]) && !Arrays.stream(subCommand.getInfo().aliases()).collect(Collectors.toList()).contains(args[0])) {
                    continue;
                }
                if (!subCommand.getInfo().permission().isEmpty() && !commandUser.hasPermission(subCommand.getInfo().permission())) {
                    continue;
                }
                ret.addAll(subCommand.tabComplete(commandUser, removeFirstElement(args)));
            }
            StringUtil.copyPartialMatches(args[args.length - 1], ret, completors);
            return completors;
        }
    }

    private CommandMap getCommandMap(Server server) {
        try {
            return (CommandMap) COMMAND_MAP_FIELD.get(server.getPluginManager());
        } catch (Exception e) {
            throw new RuntimeException("Could not get CommandMap", e);
        }
    }

    /**
     * Remove the first element of the args array.
     * @param array args
     * @return args - 1st element
     */
    protected String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }
}