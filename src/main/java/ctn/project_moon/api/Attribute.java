package ctn.project_moon.api;


import net.minecraft.world.entity.player.Player;

public class Attribute<T> {
    private final IAttributeCodec<T> codec;
    public Attribute(IAttributeCodec<T> codec) {
        this.codec = codec;
    }

    public T set(Player player, T data) {
        this.codec.codec(player.level().registryAccess(), player.getPersistentData(), data);
        return data;
    }

    public T get(Player player, T data) {
        return this.codec.decode(player.level().registryAccess(), player.getPersistentData());
    }
}
