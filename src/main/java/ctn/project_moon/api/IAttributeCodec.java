package ctn.project_moon.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public abstract class IAttributeCodec<T> {
    protected final String name;
    public IAttributeCodec(String name) {
        this.name = name;
    }

    public String getAttributeName() {
        return this.name;
    }

    abstract public void codec(HolderLookup.Provider provider, CompoundTag tag, T data);
    abstract public T decode(HolderLookup.Provider provider, CompoundTag tag);
}
