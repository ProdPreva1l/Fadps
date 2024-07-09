package info.preva1l.fadps.config;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.database.DatabaseType;
import info.preva1l.fadps.utils.BasicConfig;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
@Getter
public class Settings {
    @Getter(AccessLevel.PRIVATE)
    private final BasicConfig configFile;
    @Getter(AccessLevel.PRIVATE)
    private final String CONFIG_HEADER = "This is the main config file for Fadps.\nHere you can modify database & other misc settings.";

    public Settings(Fadps plugin) {
        this.configFile = new BasicConfig(plugin, "config.yml");
        configFile.setHeader(CONFIG_HEADER);
        this.load();
    }

    private Database database = new Database();

    @Getter
    public class Database {
        private DatabaseType type = DatabaseType.SQLITE;
        private String connectionURI = "jdbc:mysql://username:password@127.0.0.1:3306/Triumph";


        public void load() {
            type = getConfigFile().getEnum("database.type", type);
            connectionURI = getConfigFile().getString("database.uri", connectionURI);

            getConfigFile().comment("database.type", List.of("Accepted types: SQLITE"));
        }
    }

    private Editor editor = new Editor();

    @Getter
    public class Editor {
        private boolean enabled = true;

        public void load() {
            enabled = getConfigFile().getBoolean("editor.enabled", enabled);
        }
    }

    public void load() {
        configFile.load();
        database.load();
        editor.load();
    }
}