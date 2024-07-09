package info.preva1l.fadps.levels;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public record Position(
        String worldName,
        int x,
        int y,
        int z
) {
    public Location toLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}
