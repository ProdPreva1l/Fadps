package info.preva1l.fadps.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseType {
    SQLITE("sqlite", "SQLite")
    ;
    private final String id;
    private final String friendlyName;
}
