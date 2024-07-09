package info.preva1l.fadps.utils;

import info.preva1l.fadps.Fadps;
import lombok.Getter;
import lombok.Setter;
import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BasicConfig {
    private final Fadps plugin;
    private final String fileName;
    private YamlFile configuration;

    public BasicConfig(Fadps plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.configuration = new YamlFile(plugin.getDataFolder() + "/" + fileName);
        this.load();

        configuration.setCommentFormat(YamlCommentFormat.PRETTY);
    }

    public void setHeader(String header) {
        configuration.setHeader(header);
        this.save();
    }

    public void comment(String path, List<String> comments) {
        configuration.setComment(path, String.join("\n", comments));
        this.save();
    }

    public boolean getBoolean(String path, boolean def) {
        if (!configuration.contains(path)) {
            configuration.addDefault(path, def);
            this.save();
        }
        return (this.configuration.contains(path)) && (this.configuration.getBoolean(path));
    }

    public int getInt(String path, int def) {
        if (!configuration.contains(path)) {
            configuration.addDefault(path, def);
            this.save();
        }
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }

    public String getString(String path, String def) {
        if (!configuration.contains(path)) {
            configuration.addDefault(path, def);
            this.save();
        }
        if (this.configuration.contains(path)) {
            return this.configuration.getString(path);
        }
        return path;
    }

    public List<String> getStringList(String path, List<String> def) {
        if (!configuration.contains(path)) {
            configuration.addDefault(path, def);
            this.save();
        }
        if (this.configuration.contains(path)) {
            return this.configuration.getStringList(path);
        }
        return Collections.singletonList(path);
    }

    /**
     * @param path Path to get the section's keys from
     * @return Set of children of the specified path. Returns an empty set if no children are found
     */
    public Set<String> getSectionKeys(String path) {
        return configuration.getConfigurationSection(path).getKeys(false);
    }

    public <T extends Enum<T>> T getEnum(String path, T def){
        return Enum.valueOf(def.getDeclaringClass(), getString(path, def.name()));
    }

    public void load() {
        try {
            this.configuration.createOrLoadWithComments();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void save() {
        try {
            this.configuration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}