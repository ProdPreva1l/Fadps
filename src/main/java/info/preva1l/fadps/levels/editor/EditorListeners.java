package info.preva1l.fadps.levels.editor;

import info.preva1l.fadps.user.OnlineUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public final class EditorListeners implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        OnlineUser player = OnlineUser.wrap(e.getPlayer());
        if (!(player.getCurrentLevel() instanceof EditableLevel level)) {
            return;
        }
        if (level.calculateBytes(e.getBlock()) + level.getCurrentFileSize() > level.getMaxFileSize()) {
            e.setCancelled(true);
            return;
        }

        level.addToFileSize(e.getBlock());
    }
}
