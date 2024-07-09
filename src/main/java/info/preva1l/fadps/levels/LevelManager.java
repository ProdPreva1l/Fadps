package info.preva1l.fadps.levels;

import info.preva1l.fadps.levels.editor.EditableLevel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LevelManager {
    private final Map<Integer, PlayableLevel> playableLevels = new ConcurrentHashMap<>();
    private final Map<Integer, EditableLevel> editableLevels = new ConcurrentHashMap<>();


}
