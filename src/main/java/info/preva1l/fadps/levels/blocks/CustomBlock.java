package info.preva1l.fadps.levels.blocks;

import info.preva1l.fadps.utils.Text;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class CustomBlock implements Listener {
    private final BlockInfo info;

    public CustomBlock() {
        this.info = this.getClass().getAnnotation(BlockInfo.class);
        if (info == null) {
            throw new IllegalStateException("%s must be annotated with @BlockInfo"
                    .formatted(this.getClass().getSimpleName()));
        }

        if (info.id().replaceAll("\\W", "[!-!]").contains("[!-!]")) {
            throw new IllegalStateException("%s id must only contain letters, numbers or underscores!"
                    .formatted(this.getClass().getSimpleName()));
        }
    }

    public ItemStack itemStack() {
        ItemStack itemStack = new ItemStack(info.block());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Text.modernMessage(info.name()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public abstract void trigger(@NotNull Player interacted, @NotNull Location location);
}
