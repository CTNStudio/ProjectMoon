package ctn.project_moon;

import ctn.project_moon.datagen.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 数据生成主类
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MOD_ID)
public class PmDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // 客户端数据生成
        generator.addProvider(event.includeClient(), new I18ZhCnDatagen(output));
        generator.addProvider(event.includeClient(), new PmItemModel(output, exFileHelper));
        generator.addProvider(event.includeClient(), new PmBlockState(output, exFileHelper));
        PmTags.PmBlock pmPmBlockTags = new PmTags.PmBlock(output, lookupProvider, exFileHelper);
        generator.addProvider(event.includeClient(), pmPmBlockTags);
        generator.addProvider(event.includeClient(), new PmTags.PmItem(output, lookupProvider, pmPmBlockTags.contentsGetter(), exFileHelper));
        generator.addProvider(event.includeClient(), new PmTags.PmDamageType(output, lookupProvider, exFileHelper));
        generator.addProvider(event.includeClient(), new PmTags.PmEntity(output, lookupProvider, exFileHelper));

        // 服务端数据生成
        generator.addProvider(event.includeServer(), new PmDatapackBuiltinEntriesProvider(output, lookupProvider));
    }
}
