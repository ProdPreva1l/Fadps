package info.preva1l.fadps.leaderboards;

import info.preva1l.fadps.Fadps;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class LeaderboardPlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "leaderboards";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Preva1l";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

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
