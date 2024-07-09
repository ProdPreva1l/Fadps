package info.preva1l.fadps.levels.editor;

import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import info.preva1l.fadps.Fadps;
import info.preva1l.fadps.levels.Level;
import info.preva1l.fadps.levels.PlayableLevel;
import info.preva1l.fadps.levels.Schematic;
import info.preva1l.fadps.user.OnlineUser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;

@Getter
public final class EditableLevel extends Level {
    private final Schematic schematic;

    private final double centerX;
    private final double centerZ;
    private final int size = 33;

    /**
     * Current file size in bytes
     */
    private int currentFileSize = 0;
    /**
     * Max file size in bytes
     */
    private int maxFileSize;

    private EditableLevel(int id, String name, double centerX, double centerZ, @NotNull OnlineUser player) {
        super(id, name, player.unlink(), "edit.schem", BoundingBox.of(new Location(Bukkit.getWorld("parkour"), centerX, 77.5, centerZ), 1.5, 141.5, 1.5));
        this.centerX = centerX;
        this.centerZ = centerZ;

        this.schematic = new Schematic("");
        this.maxFileSize = player.getMaxEditorSize();
    }

    public void save() {
        Bukkit.getScheduler().runTaskAsynchronously(Fadps.getInstance(), () -> {
            try (ClipboardWriter writer = BuiltInClipboardFormat.MCEDIT_SCHEMATIC.getWriter(new FileOutputStream(schematic.getFile()))) {
                writer.write(schematic.getFromCuboid(CuboidRegion.fromCenter(BlockVector3.at(centerX, 77.5, centerZ), size)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int calculateBytes(Block block) {
        int bytes = 0;
        // Material ID (Integer is 4 bytes)
        bytes += 4;
        // Metadata bytes (this is probably not very accurate, but it's the only way I can think of doing it)
        bytes += block.getBlockData().getAsString().getBytes().length;
        return bytes;
    }

    public void addToFileSize(Block block) {
        currentFileSize += calculateBytes(block);
    }

    public void removeFromFileSize(Block block) {
        currentFileSize -= calculateBytes(block);
    }

    public PlayableLevel makePlayable() {
        return PlayableLevel.constructFromDatabase(getOwner(), 0, 0, 0, 0,0,0);
    }
}
