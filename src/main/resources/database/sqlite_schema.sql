CREATE TABLE IF NOT EXISTS editable_levels (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    owner TEXT NOT NULL,
    schematicFile TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS playable_levels (
   id INTEGER NOT NULL PRIMARY KEY,
   name TEXT NOT NULL,
   owner TEXT NOT NULL,
   schematicFile TEXT NOT NULL
);