package info.preva1l.fadps.levels.editor;

import info.preva1l.fadps.user.OnlineUser;
import info.preva1l.fadps.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class EditorTask extends BukkitRunnable {
    private final OnlineUser viewer;
    private final EditableLevel level;
    private final BossBar bossBar;

    private static final String BAR_FORMAT = "&fStorage Usage: &a{0}&f/&a{1} KB &7(&f{2}%&7)";

    public EditorTask(@NotNull Player viewer) {
        this.viewer = OnlineUser.wrap(viewer);
        if (this.viewer.getCurrentLevel() instanceof EditableLevel editorLevel) {
            this.level = editorLevel;
        } else {
            throw new IllegalStateException("Tried to create usage bar for a player who is not in an editor level!");
        }
        this.bossBar = Bukkit.createBossBar(Text.legacyMessage(BAR_FORMAT, "??", "???", "?"), BarColor.GREEN, BarStyle.SOLID);
        bossBar.addPlayer(viewer);
    }

    @Override
    public void run() {
        if (!viewer.getCurrentLevel().equals(level)) {
            this.cancel();
            viewer.getAudience().setWorldBorder(null);
            bossBar.setVisible(false);
            bossBar.removeAll();
            return;
        }
        WorldBorder worldBorder = Bukkit.createWorldBorder();
        worldBorder.setCenter(level.getCenterX(), level.getCenterZ());
        worldBorder.setSize(level.getSize());
        viewer.getAudience().setWorldBorder(worldBorder);

        int used = level.getCurrentFileSize();
        int max = level.getMaxFileSize();
        int percent = used/max * 100;

        BarColor color = BarColor.GREEN;
        if (percent >= 75) color = BarColor.RED;
        if (percent >= 55) color = BarColor.YELLOW;

        bossBar.setProgress(percent / 100D);
        bossBar.setTitle(Text.legacyMessage(BAR_FORMAT, used, max, percent));
        bossBar.setColor(color);
    }
}
