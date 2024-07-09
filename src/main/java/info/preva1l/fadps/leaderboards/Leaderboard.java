package info.preva1l.fadps.leaderboards;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Leaderboard {
    LEVELS_COMPLETE("levels")
    ;

    private final String identifier;

    public int sString() {
        return getIdentifier().length();
    }

    public boolean isLeaderboard(String param) {
        return param.startsWith(identifier + "_");
    }
}
