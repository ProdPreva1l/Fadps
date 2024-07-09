package info.preva1l.fadps.levels;

import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.Getter;

import java.io.*;
import java.util.concurrent.CompletableFuture;

@Getter
public final class Schematic {
    private final File file;
    private final String schematicFileName;

    public Schematic(String schematicFileName) {
        this.schematicFileName = schematicFileName;
        this.file = new File(schematicFileName);
    }

    public Clipboard getFromCuboid(CuboidRegion cuboidRegion) {
        return new BlockArrayClipboard(cuboidRegion);
    }

    public CompletableFuture<Clipboard> getClipboardFromFile() {
        return CompletableFuture.supplyAsync(() -> {
            Clipboard clipboard;
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            if (format == null) {
                format = BuiltInClipboardFormat.MCEDIT_SCHEMATIC;
            }
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                clipboard = reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return clipboard;
        });
    }
}
