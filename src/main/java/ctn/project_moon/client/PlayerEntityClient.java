package ctn.project_moon.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerEntityClient {
    // 本地客户端理智值 TODO 将这些变为实体属性
    public static float spiritValue;
    public static float maxSpiritBalue;
}
