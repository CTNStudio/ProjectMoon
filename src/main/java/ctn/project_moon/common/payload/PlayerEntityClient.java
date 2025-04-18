package ctn.project_moon.common.payload;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerEntityClient {
    // 本地客户端理智值
    private static float spiritValue;
    private static float maxSpiritBalue;

    public static float getSpiritValue() {
        return spiritValue;
    }

    public static void setSpiritValue(float spiritValue) {
        PlayerEntityClient.spiritValue = spiritValue;
    }

    public static float getMaxSpiritBalue() {
        return maxSpiritBalue;
    }

    public static void setMaxSpiritBalue(float maxSpiritBalue) {
        PlayerEntityClient.maxSpiritBalue = maxSpiritBalue;
    }

    
}
