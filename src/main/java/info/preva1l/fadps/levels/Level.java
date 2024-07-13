package info.preva1l.fadps.levels;

import info.preva1l.fadps.user.User;
import lombok.*;
import org.bukkit.util.BoundingBox;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Level {
    private final int id;
    private final String name;
    private final User owner;
    private final String schematicFileName;
    private final BoundingBox checkpoint;

    @Setter private Difficulty difficulty = Difficulty.EASY;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Level level) {
            return level.id == this.id;
        }
        return false;
    }
}
