package info.preva1l.fadps.levels;

import com.sk89q.worldedit.math.Vector3;
import info.preva1l.fadps.user.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

@Getter
public final class PlayableLevel extends Level {
    private final Position firstCorner;
    private final Position secondCorner;

    private PlayableLevel(User owner, BoundingBox checkpoint, Position firstCorner, Position secondCorner) {
        super(1, "", owner, "test.schem", checkpoint);
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
    }

    public static PlayableLevel constructFromDatabase(User owner,
                                                      double checkpointX, double checkpointZ,
                                                      int firstCornerX, int firstCornerZ,
                                                      int secondCornerX, int secondCornerZ) {
        return new PlayableLevel(owner,
                BoundingBox.of(new Location(Bukkit.getWorld("parkour"), checkpointX, 77.5, checkpointZ), checkpointX, checkpointZ, checkpointZ),
                new Position("parkour", firstCornerX, -64, firstCornerZ),
                new Position("parkour", secondCornerX, 219, secondCornerZ));
    }
}
