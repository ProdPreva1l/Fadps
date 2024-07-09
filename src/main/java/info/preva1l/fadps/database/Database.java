package info.preva1l.fadps.database;

import info.preva1l.fadps.leaderboards.Leaderboard;
import info.preva1l.fadps.levels.editor.EditableLevel;
import info.preva1l.fadps.user.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Database {
    void connect();
    void destroy();

    CompletableFuture<List<EditableLevel>> getEditableLevels(User user);
    void loadPlayableLevels();
    CompletableFuture<Map<Integer, User>> getLeaderboard(Leaderboard leaderboard);
}
