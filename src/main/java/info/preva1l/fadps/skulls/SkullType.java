package info.preva1l.fadps.skulls;

import info.preva1l.fadps.Fadps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public enum SkullType {
    NONE("2a52d579afe2fdf7b8ecfa746cd016150d96beb75009bb2733ade15d487c42a1"),
    LIME_GREEN("a7695f96dda626faaa010f4a5f28a53cd66f77de0cc280e7c5825ad65eedc72e"),
    ;

    private final String URL;

    public CompletableFuture<ItemStack> getSkullItem() {
        return Fadps.getInstance().getSkullCache().getSkull(this);
    }
}
