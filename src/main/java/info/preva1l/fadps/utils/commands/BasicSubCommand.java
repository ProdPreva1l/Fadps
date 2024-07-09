package info.preva1l.fadps.utils.commands;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.user.CommandUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BasicSubCommand {
    public Fadps plugin;
    private final Command info;

    public BasicSubCommand(Fadps plugin) {
        this.plugin = plugin;
        this.info = Arrays.stream(this.getClass().getConstructors())
                .filter(method -> method.getAnnotation(Command.class) != null)
                .map(method -> method.getAnnotation(Command.class)).findFirst().orElse(null);

        if (info == null) {
            throw new RuntimeException("%s constructor must be annotated with @Command"
                    .formatted(this.getClass().getSimpleName()));
        }
    }

    public abstract void execute(CommandUser sender, String[] args);

    public List<String> tabComplete(CommandUser sender, String[] args) {
        return new ArrayList<>();
    }
}