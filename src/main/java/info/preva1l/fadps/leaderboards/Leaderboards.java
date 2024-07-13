package info.preva1l.fadps.leaderboards;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.user.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Leaderboards {
    private final Map<Integer, User> levels = new ConcurrentHashMap<>();

    public String getUsernameAtPosition(int position) {
        return levels.get(position).getUsername();
    }

    public int getLeaderboardPosition(User user) {
        for (int position : levels.keySet()) {
            if (levels.get(position).equals(user)) return position;
        }
        return -1;
    }

    public void loadLeaderboards() {
        levels.clear();
        Fadps.getInstance().getDatabase().getLeaderboard(Leaderboard.LEVELS_COMPLETE).thenAccept(levels::putAll);


    }
}
