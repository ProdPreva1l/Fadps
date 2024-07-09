package info.preva1l.fadps.user;

import info.preva1l.fadps.Fadps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleUser implements CommandUser {
    private final Audience audience;
    private final Fadps plugin;

    @NotNull
    public static ConsoleUser wrap(@NotNull Audience audience) {
        return new ConsoleUser(audience, Fadps.getInstance());
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }
}