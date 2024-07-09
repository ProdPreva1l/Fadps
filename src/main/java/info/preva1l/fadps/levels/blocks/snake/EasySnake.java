package info.preva1l.fadps.levels.blocks.snake;

import info.preva1l.fadps.levels.blocks.BlockInfo;
import info.preva1l.fadps.levels.blocks.CustomBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@BlockInfo(
        id = "snake_easy",
        name = "&a&lEasy &2Snake",
        description = {},
        block = Material.LIME_CONCRETE
)
public final class EasySnake extends CustomBlock {
    @Override
    public void trigger(@NotNull Player interacted, @NotNull Location location) {

    }
}
