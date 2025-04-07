package ctn.project_moon;

import com.mojang.logging.LogUtils;
import ctn.project_moon.init.PmBlocks;
import ctn.project_moon.init.PmItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static ctn.project_moon.init.PmTab.PROJECT_MOON_TAB;

/**
 * 本模组主类
 * <p>
 * 包类：{@link ctn.project_moon.api}-没有特定或固定内容
 * <p>
 * 包类：{@link ctn.project_moon.client}-仅用于客户端
 * <p>
 * 包类：{@link ctn.project_moon.common}-可重复使用的类如：方块、物品、方块实体等
 * <p>
 * 包类：{@link ctn.project_moon.datagen}-数据生成器
 * <p>
 * 包类：{@link ctn.project_moon.events}-事件
 * <p>
 * 包类：{@link ctn.project_moon.mixin}-mixin 注入
 * <p>
 * 包类：{@link ctn.project_moon.server}-仅用于服务端
 */
@Mod(PmMain.MOD_ID)
public class PmMain {
    public static final String MOD_ID = "project_moon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PmMain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        PmBlocks.BLOCKS.register(modEventBus);
        PmItems.ITEMS.register(modEventBus);
        PROJECT_MOON_TAB.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
