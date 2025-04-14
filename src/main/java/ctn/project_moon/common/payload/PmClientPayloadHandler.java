package ctn.project_moon.common.payload;

import ctn.project_moon.client.PlayerEntityClient;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PmClientPayloadHandler {
    public static void spiritValue(final SpiritValueDelivery data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    PlayerEntityClient.setSpiritValue(data.spiritValue());
                    PlayerEntityClient.setMaxSpiritBalue(data.maxSpiritValue());
        });
    }
}
