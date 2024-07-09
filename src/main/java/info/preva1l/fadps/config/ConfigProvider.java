package info.preva1l.fadps.config;

import info.preva1l.fadps.Fadps;
import org.jetbrains.annotations.NotNull;

public interface ConfigProvider {
    @NotNull Settings getSettings();

    @NotNull Lang getLang();

    void loadConfigs();

    /**
     * Reload configuration files
     * @param plugin instance of triumph
     * @return true if success, false if error thrown
     */
    default boolean reloadConfigs(Fadps plugin) {
        try {
            getSettings().load();
            getLang().load();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }
}