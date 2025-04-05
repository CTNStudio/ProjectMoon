package ctn.project_moon.common.payload;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

import static ctn.project_moon.PmMain.MOD_ID;

public record SpiritValueDelivery(float spiritValue, float maxSpiritValue, float minSpiritValue) implements CustomPacketPayload {

    // TODO 这里
    public static final CustomPacketPayload.Type<SpiritValueDelivery> value = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_value_delivery"));
    public static final StreamCodec<BeeInfo, SpiritValueDelivery> STREAM_CODEC =
            CustomPacketPayload.codec(SpiritValueDelivery::write, SpiritValueDelivery::new);

    private void write(ByteBuf buffer) {
        this.beeInfo.write(buffer);
    }

    public record BeeInfo(
            float spiritValue, float maxSpiritValue, float minSpiritValue
    ) {
        public BeeInfo(ByteBuf p_295195_) {
            this();
        }

        public void write(ByteBuf buffer) {
            buffer.
        }

        public boolean hasHive(BlockPos pos) {
            return Objects.equals(pos, this.hivePos);
        }

        public String generateName() {
            return DebugEntityNameGenerator.getEntityName(this.uuid);
        }

        @Override
        public String toString() {
            return this.generateName();
        }
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return value;
    }
}
