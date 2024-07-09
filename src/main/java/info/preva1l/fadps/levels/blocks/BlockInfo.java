package info.preva1l.fadps.levels.blocks;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BlockInfo {
    @NotNull String id();

    @NotNull String name();

    @NotNull String[] description();

    @NotNull Material block();
}
