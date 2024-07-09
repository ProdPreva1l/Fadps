package info.preva1l.fadps.user;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.utils.Text;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandUser {
    Fadps getPlugin();

    @NotNull Audience getAudience();

    boolean hasPermission(@NotNull String permission);

    default void sendMessage(@NotNull String message, Object... replacement) {
        getAudience().sendMessage(Text.modernMessage("&#32a852&lFadps " + message, replacement));
    }

    default void sendMessage(@NotNull List<String> message, Object... replacement) {
        getAudience().sendMessage(Text.modernMessage(String.join("\n", message), replacement));
    }

    default void sendRawMessage(@NotNull String message, Object... replacement) {
        getAudience().sendMessage(Text.modernMessage(message, replacement));
    }
}