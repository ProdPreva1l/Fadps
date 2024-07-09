package info.preva1l.fadps.skulls;

import com.destroystokyo.paper.profile.PlayerProfile;
import info.preva1l.fadps.Fadps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class SkullCache {
    private final Fadps plugin;
    private final Map<SkullType, ItemStack> cache = new ConcurrentHashMap<>();
    private static final String baseMojangURL = "https://textures.minecraft.net/texture/";

    public SkullCache(Fadps plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<ItemStack> getSkull(SkullType skullType) {
        ItemStack cachedSkull = cache.get(skullType);
        if (cachedSkull == null) {
            return getSkullFromMojang(skullType);
        }
        return CompletableFuture.completedFuture(cachedSkull);
    }

    private CompletableFuture<ItemStack> getSkullFromMojang(SkullType skullType) {
        return CompletableFuture.supplyAsync(() -> {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            if (skullType.getURL().isEmpty()) return skull;

            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);

            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(URI.create(baseMojangURL + skullType.getURL()).toURL());
            } catch (MalformedURLException e) {
                plugin.getLogger().severe("Invalid URL \"%s\""
                        .formatted(baseMojangURL + skullType.getURL()));
                return skull;
            }
            profile.setTextures(textures);

            skullMeta.setPlayerProfile(profile);
            skull.setItemMeta(skullMeta);

            cache.put(skullType, skull);
            return skull;
        });
    }
}
