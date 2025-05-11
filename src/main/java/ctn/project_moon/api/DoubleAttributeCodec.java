package ctn.project_moon.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class DoubleAttributeCodec extends IAttributeCodec<Double> {
    public DoubleAttributeCodec(String name) {
        super(name);
    }

    @Override
    public void codec(HolderLookup.Provider provider, CompoundTag tag, Double data) {
        tag.putDouble(this.getAttributeName(), data);
    }

    @Override
    public Double decode(HolderLookup.Provider provider, CompoundTag tag) {
        return tag.getDouble(this.getAttributeName());
    }
}
