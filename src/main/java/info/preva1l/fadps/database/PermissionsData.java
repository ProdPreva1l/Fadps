package info.preva1l.fadps.database;

import info.preva1l.fadps.user.OnlineUser;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

@UtilityClass
public class PermissionsData {
    public int getHighestInt(PermissionType type, OnlineUser player) {
        int currentMax = 0;
        boolean matched = false;
        final Set<PermissionAttachmentInfo> finalEffectivePermissions = player.getAudience().getEffectivePermissions(); // "Thread Safe"
        for (PermissionAttachmentInfo effectivePermission : finalEffectivePermissions) {
            if (!effectivePermission.getPermission().startsWith(type.permissionString)) continue;
            String numberStr = effectivePermission.getPermission().substring(type.permissionString.length());
            try {
                if (currentMax < Integer.parseInt(numberStr)) {
                    currentMax = Integer.parseInt(numberStr);
                    matched = true;
                }
            } catch (NumberFormatException ignored) {}
        }
        return matched ? currentMax : (int) type.def;
    }

    public double getHighestDouble(PermissionType type, OnlineUser player) {
        double currentMax = 0;
        boolean matched = false;
        final Set<PermissionAttachmentInfo> finalEffectivePermissions = player.getAudience().getEffectivePermissions(); // "Thread Safe"
        for (PermissionAttachmentInfo effectivePermission : finalEffectivePermissions) {
            if (!effectivePermission.getPermission().startsWith(type.permissionString)) continue;
            String numberStr = effectivePermission.getPermission().substring(type.permissionString.length());
            try {
                if (currentMax < Double.parseDouble(numberStr)) {
                    currentMax = Double.parseDouble(numberStr);
                    matched = true;
                }
            } catch (NumberFormatException ignored) {}
        }
        return matched ? currentMax : (double) type.def;
    }

    @AllArgsConstructor
    public enum PermissionType {
        MAX_EDITOR_SIZE("fadps.max-editor-size.", 500),
        ;
        private final String permissionString;
        private final Object def;
    }
}