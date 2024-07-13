package info.preva1l.fadps.sidebar;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.editor.EditableLevel;
import info.preva1l.fadps.user.OnlineUser;
import info.preva1l.fadps.utils.Text;
import info.preva1l.fadps.utils.scoreboards.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SidebarManager implements Listener {
    private static final String FORMAT = """
            &fServer
            &r  &fOnline: &a%player_count%
            &r  &fLevels: &9%all_levels%
            &f%player%
            &r  &fLevel: &3%current_level%
            &r  &fRank: &6%leaderboard_position%
            &r  &fPlaytime: &1%formatted_playtime%
            """;

    private static final String EDITOR_FORMAT = """
            &fServer
            &r  &fOnline: &a%player_count%
            &r  &fLevels: &9%all_levels%
            &fEditor
            &r  &fName: &3%level_name%
            &r  &fSize: &6%level_size% bytes
            &r  &fMax Size: &1%level_max_size% bytes
            """;

    private final Fadps plugin;
    private final Map<UUID, FastBoard> boards = new ConcurrentHashMap<>();

    public SidebarManager(Fadps plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FastBoard board = new FastBoard(event.getPlayer());
        board.updateTitle(Text.legacyMessage("&#32a852&lFinally a Decent Parkour Server"));
        boards.put(event.getPlayer().getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    private void updateBoard(FastBoard board) {
        OnlineUser user = OnlineUser.wrap(board.getPlayer());

        if (user.getCurrentLevel() instanceof EditableLevel editableLevel) {
            board.updateLines(Text.legacyMessage(EDITOR_FORMAT
                    .replace("%player_count%", Bukkit.getOnlinePlayers().size() + "")
                    .replace("%all_levels%", plugin.getLevelManager().getLevelCount() + "")

                    .replace("%level_name%", editableLevel.getName())
                    .replace("%level_size%", editableLevel.getSize() + "")
                    .replace("%level_max_size%", editableLevel.getMaxFileSize() + "")
            ));
        }

        board.updateLines(Text.legacyMessage(FORMAT
                .replace("%player%", user.getUsername())
                .replace("%player_count%", Bukkit.getOnlinePlayers().size() + "")
                .replace("%all_levels%", plugin.getLevelManager().getLevelCount() + "")

                .replace("%current_level%", user.getCurrentLevel().getId() + "")
                .replace("%leaderboard_position%", user.getLeaderboardPosition() + "")
                .replace("%formatted_playtime%", user.getPlaytime())
        ));
    }
}
