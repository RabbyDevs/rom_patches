package com.rabbydevs.rom_patches;

import com.rabbydevs.rom_patches.loot.WardenDropsModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ROMPatches.MODID)
public class ROMPatches {
    public static final String MODID = "rom_patches";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static boolean isNetherRemasteredLoaded = false;

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<WardenDropsModifier>>
            WARDEN_DROPS = LOOT_MODIFIERS.register("warden_drops", () -> WardenDropsModifier.CODEC);

    public ROMPatches(IEventBus modEventBus) {
        LOOT_MODIFIERS.register(modEventBus);
        modEventBus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        isNetherRemasteredLoaded = ModList.get().isLoaded("nether_remastered");

        if (!isNetherRemasteredLoaded) {
            LOGGER.warn("Nether Remastered mod is not loaded. ROM Patches will still register its loot modifiers, but the items may not exist until Nether Remastered is installed.");
        } else {
            LOGGER.info("Nether Remastered mod detected. ROM Patches loot modifiers will function properly.");
        }
    }
}