package info.preva1l.fadps;

import info.preva1l.fadps.config.ConfigProvider;
import info.preva1l.fadps.config.Lang;
import info.preva1l.fadps.config.Settings;
import info.preva1l.fadps.database.Database;
import info.preva1l.fadps.database.SQLiteDatabase;
import info.preva1l.fadps.leaderboards.Leaderboards;
import info.preva1l.fadps.levels.LevelManager;
import info.preva1l.fadps.levels.blocks.BlockManager;
import info.preva1l.fadps.skulls.SkullCache;
import info.preva1l.fadps.utils.TaskProvider;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
public final class Fadps extends JavaPlugin implements TaskProvider, ConfigProvider {
    @Getter private static Fadps instance;

    private Settings settings;
    private Lang lang;

    private Database database;
    private SkullCache skullCache;

    private LevelManager levelManager;
    private BlockManager blockManager;

    private Leaderboards leaderboards;

    @Override
    public void onEnable() {
        instance = this;
        loadConfigs();

        database = new SQLiteDatabase(this);
        skullCache = new SkullCache(this);

        levelManager = new LevelManager();
        blockManager = new BlockManager();
        leaderboards = new Leaderboards();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void loadConfigs() {
        lang = new Lang(this);
        settings = new Settings(this);
    }

    @Override
    public void executeAsync(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    @Override
    public void executeRepeatingAsync(Runnable runnable, Duration repeatingTime) {
        getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, 0L, repeatingTime.get(ChronoUnit.SECONDS) * 20);
    }

    @Override
    public void executeSync(Runnable runnable) {
        getServer().getScheduler().runTask(this, runnable);
    }

    @Override
    public void executeRepeatingSync(Runnable runnable, Duration repeatingTime) {
        getServer().getScheduler().runTaskTimer(this, runnable, 0L, repeatingTime.get(ChronoUnit.SECONDS) * 20);
    }
}
