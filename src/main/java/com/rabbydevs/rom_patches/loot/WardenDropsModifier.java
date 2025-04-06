package com.rabbydevs.rom_patches.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class WardenDropsModifier extends LootModifier {
    public static final MapCodec<WardenDropsModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).apply(inst, WardenDropsModifier::new)
    );

    public WardenDropsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Add the drops one by one using registry key strings directly
        addItemIfExists(generatedLoot, "nether_remastered:seal_piece_1", 1);
        addItemIfExists(generatedLoot, "nether_remastered:seal_piece_2", 1);
        addItemIfExists(generatedLoot, "nether_remastered:seal_piece_3", 1);
        addItemIfExists(generatedLoot, "nether_remastered:seal_piece_4", 1);
        addItemIfExists(generatedLoot, "nether_remastered:bottled_nether_essence", 4);
        addItemIfExists(generatedLoot, "nether_remastered:seal_crystal", 1);

        // Add vanilla items (carrots and potatoes)
        generatedLoot.add(new ItemStack(Items.CARROT, 12));
        generatedLoot.add(new ItemStack(Items.POTATO, 12));

        return generatedLoot;
    }

    private void addItemIfExists(ObjectArrayList<ItemStack> generatedLoot, String registryName, int count) {
        // Get item by registry name string using BuiltInRegistries instead of ForgeRegistries
        ResourceLocation resourceLocation = ResourceLocation.tryParse(registryName);
        if (resourceLocation != null) {
            Item item = BuiltInRegistries.ITEM.get(resourceLocation);
            generatedLoot.add(new ItemStack(item, count));
        }
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}