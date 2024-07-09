package info.preva1l.fadps.user;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    @Expose
    private final String username;
    @Expose
    private final UUID uniqueId;

    @NotNull
    public static User of(@NotNull String username, @NotNull UUID uuid) {
        return new User(username, uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        return ((User) obj).getUniqueId().equals(this.uniqueId);
    }
}