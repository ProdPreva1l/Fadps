package info.preva1l.fadps.user;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.Level;
import info.preva1l.fadps.levels.PlayableLevel;
import info.preva1l.fadps.levels.editor.EditableLevel;
import info.preva1l.fadps.utils.Text;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
public final class OnlineUser extends User implements CommandUser {
    private final Player audience;
    private final Fadps plugin;

    private OnlineUser(@NotNull Player player) {
        super(player.getName(), player.getUniqueId());
        this.audience = player;
        this.plugin = Fadps.getInstance();
    }

    public static OnlineUser wrap(Player player) {
        return new OnlineUser(player);
    }

    public Level getCurrentLevel() {
        return PlayableLevel.constructFromDatabase(this, 0, 0, 0, 0, 0, 0);
    }

    public int getMaxEditorSize() {
        return 500;
    }

    public void sendClickableMessage(@NotNull String message, @NotNull ClickEvent clickEvent, Object... replacements) {
        audience.sendMessage(Text.modernMessage( message, replacements).clickEvent(clickEvent));
    }

    public void sendHoverMessage(@NotNull String message, @NotNull HoverEvent<Component> hoverEvent, Object... replacements) {
        audience.sendMessage(Text.modernMessage(message, replacements).hoverEvent(hoverEvent));
    }

    public void sendActionBar(@NotNull String message, Object... replacements) {
        audience.sendActionBar(Text.modernMessage(message, replacements));
    }

    public void sendTitle(@NotNull String titleText, @Nullable String subTitle, long fadeIn, long stay, long fadeOut) {
        if (subTitle == null) {
            subTitle = "";
        }
        Title.Times times = Title.Times.times(
                Duration.of(fadeIn, ChronoUnit.MILLIS),
                Duration.of(stay, ChronoUnit.MILLIS),
                Duration.of(fadeOut, ChronoUnit.MILLIS));
        Title title = Title.title(Text.modernMessage(titleText), Text.modernMessage(subTitle), times);
        audience.showTitle(title);
    }

    public void sendTitle(@NotNull String titleText, @Nullable String subTitle) {
        if (subTitle == null) {
            subTitle = "";
        }
        Title title = Title.title(Text.modernMessage(titleText), Text.modernMessage(subTitle));
        audience.showTitle(title);
    }

    public User unlink() {
        return User.of(getUsername(), getUniqueId());
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return false;
    }
}