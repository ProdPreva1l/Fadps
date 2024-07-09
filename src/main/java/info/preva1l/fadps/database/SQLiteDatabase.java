package info.preva1l.fadps.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.leaderboards.Leaderboard;
import info.preva1l.fadps.levels.editor.EditableLevel;
import info.preva1l.fadps.user.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class SQLiteDatabase implements Database {
    private final Fadps plugin;

    private static final String DATABASE_FILE_NAME = "LevelMetadata.db";
    @Getter @Setter private boolean connected = false;
    private File databaseFile;
    private HikariDataSource hikariDataSource;

    public SQLiteDatabase(Fadps plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("SameParameterValue")
    @NotNull
    private String[] getSchemaStatements(@NotNull String schemaFileName) throws IOException {
        return new String(Objects.requireNonNull(Fadps.getInstance().getResource(schemaFileName))
                .readAllBytes(), StandardCharsets.UTF_8).split(";");
    }

    private Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void setConnection() {
        try {
            if (databaseFile.createNewFile()) {
                Fadps.getInstance().getLogger().info("Created the SQLite database file");
            }

            Class.forName("org.sqlite.JDBC");

            HikariConfig config = new HikariConfig();
            config.setPoolName("LevelMetadata");
            config.setDriverClassName("org.sqlite.JDBC");
            config.setJdbcUrl("jdbc:sqlite:" + databaseFile.getAbsolutePath());
            config.setConnectionTestQuery("SELECT 1");
            config.setMaxLifetime(60000);
            config.setIdleTimeout(45000);
            config.setMaximumPoolSize(50);
            hikariDataSource = new HikariDataSource(config);

        } catch (IOException e) {
            Fadps.getInstance().getLogger().log(Level.SEVERE, "An exception occurred creating the database file", e);
        } catch (ClassNotFoundException e) {
            Fadps.getInstance().getLogger().log(Level.SEVERE, "Failed to load the necessary SQLite driver", e);
        }
    }

    private void backupFlatFile(@NotNull File file) {
        if (!file.exists()) {
            return;
        }

        final File backup = new File(file.getParent(), String.format("%s.bak", file.getName()));
        try {
            if (!backup.exists() || backup.delete()) {
                Files.copy(file.toPath(), backup.toPath());
            }
        } catch (IOException e) {
            Fadps.getInstance().getLogger().log(Level.WARNING, "Failed to backup flat file database", e);
        }
    }

    @Override
    public void connect() {
        databaseFile = new File(Fadps.getInstance().getDataFolder(), DATABASE_FILE_NAME);

        this.setConnection();
        this.backupFlatFile(databaseFile);

        try {
            final String[] databaseSchema = getSchemaStatements(String.format("database/%s_schema.sql", DatabaseType.SQLITE.getId()));
            try (Statement statement = getConnection().createStatement()) {
                for (String tableCreationStatement : databaseSchema) {
                    statement.execute(tableCreationStatement);
                }
            } catch (SQLException e) {
                destroy();
                throw new IllegalStateException("Failed to create database tables.", e);
            }
        } catch (IOException e) {
            destroy();
            throw new IllegalStateException("Failed to create database tables.", e);
        }
        setConnected(true);
    }

    @Override
    public void destroy() {

    }

    @Override
    public CompletableFuture<List<EditableLevel>> getEditableLevels(User user) {
        if (!isConnected()) {
            plugin.getLogger().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(null);
        }
        return null;
    }

    @Override
    public void loadPlayableLevels() {
        if (!isConnected()) {
            plugin.getLogger().severe("Tried to perform database action when the database is not connected!");
            return;
        }
        return;
    }

    @Override
    public CompletableFuture<Map<Integer, User>> getLeaderboard(Leaderboard leaderboard) {
        if (!isConnected()) {
            plugin.getLogger().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(Collections.emptyMap());
        }
        return null;
    }
}
