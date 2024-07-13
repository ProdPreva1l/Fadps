package info.preva1l.fadps.levels;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.editor.EditableLevel;
import info.preva1l.fadps.levels.editor.EditorListeners;
import info.preva1l.fadps.user.OnlineUser;
import info.preva1l.fadps.user.User;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class LevelManager {
    private final Map<Integer, PlayableLevel> playableLevels = new ConcurrentHashMap<>();
    private final Map<Integer, EditableLevel> editableLevels = new ConcurrentHashMap<>();

    public LevelManager(Fadps plugin) {
        Bukkit.getPluginManager().registerEvents(new EditorListeners(), plugin);
        editableLevels.put(1224, new EditableLevel(23245, "Test", 0, 0, User.of("Preva1l", UUID.fromString("26decebc-0c64-453f-98c5-939c42d17a08"))));
    }

    public Level getCurrentLevel(OnlineUser user) {
        return editableLevels.get(1124);
    }

    public int getLevelCount() {
        return playableLevels.size();
    }
}
