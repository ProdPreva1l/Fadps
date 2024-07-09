package info.preva1l.fadps.leaderboards;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.user.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Leaderboards {
    public final Map<Integer, User> levels = new ConcurrentHashMap<>();

    public String getUsernameAtPosition(int position) {
        return levels.get(position).getUsername();
    }

    public void loadLeaderboards() {
        levels.clear();
        Fadps.getInstance().getDatabase().getLeaderboard(Leaderboard.LEVELS_COMPLETE).thenAccept(levels::putAll);


    }
}
