package info.preva1l.fadps.config;

import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.utils.BasicConfig;
import lombok.AccessLevel;
import lombok.Getter;

@SuppressWarnings("FieldMayBeFinal")
@Getter
public class Lang {
    @Getter(AccessLevel.PRIVATE)
    private final BasicConfig configFile;
    @Getter(AccessLevel.PRIVATE)
    private final String CONFIG_HEADER = "This is the language file for Fadps.\nMessages used frequently throughout the plugin are found here.";

    public Lang(Fadps plugin) {
        this.configFile = new BasicConfig(plugin, "lang.yml");
        configFile.setHeader(CONFIG_HEADER);
        this.load();
    }

    private String prefix = "&#32a852&lFadps ";

    private Commands commands = new Commands();

    @Getter
    public class Commands {
        private String mustBePlayer = "&cThis command must be ran by a player!";
        private String noPermission = "&cYou do not have permission to run this command!";
        private String doesNotExist = "&cThis command does not exist!";
        private String playerNotOnline = "&cThis player is not online!";
        private String incorrectArgs = "&cInvalid Usage! &fTry: %command_usage%";

        public void load() {
            mustBePlayer = getConfigFile().getString("commands.must-be-player", mustBePlayer);
            noPermission = getConfigFile().getString("commands.no-permission", noPermission);
            doesNotExist = getConfigFile().getString("commands.does-not-exist", doesNotExist);
            playerNotOnline = getConfigFile().getString("commands.player-not-online", playerNotOnline);
            incorrectArgs = getConfigFile().getString("commands.incorrect-args", incorrectArgs);
        }
    }

    public void load() {
        configFile.load();

        prefix = getConfigFile().getString("prefix", prefix);

        commands.load();
    }
}