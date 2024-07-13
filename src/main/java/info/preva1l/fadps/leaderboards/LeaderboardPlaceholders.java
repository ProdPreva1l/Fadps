package info.preva1l.fadps.leaderboards;

import info.preva1l.fadps.Fadps;
import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@Getter
public class LeaderboardPlaceholders extends PlaceholderExpansion {
    private final String identifier = "leaderboards";
    private final String author = "Preva1l";
    private final String version = "1.0.0";
    private final boolean persist = true;

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (Leaderboard.LEVELS_COMPLETE.isLeaderboard(params)) {
            String numStr = params.substring(Leaderboard.LEVELS_COMPLETE.sString());
            int num;
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException unused) {
                return "Please use the format: %leaderboards_levels_1%";
            }
            return Fadps.getInstance().getLeaderboards().getUsernameAtPosition(num);
        }



        return "Invalid Leaderboard!";
    }
}
