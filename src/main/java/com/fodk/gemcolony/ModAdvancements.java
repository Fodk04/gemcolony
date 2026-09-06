package com.fodk.gemcolony;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger.TriggerInstance.placedBlock;

public class ModAdvancements extends AdvancementProvider {

    public ModAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new GemFoodsAdvancements()));
    }

    public static class GemFoodsAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
            var items = registries.lookupOrThrow(Registries.ITEM);

            AdvancementHolder plantStrawberry = Advancement.Builder.advancement()
                    .display(
                            ModItems.STRAWBERRY,
                            Component.translatable("advancements.gemcolony.plant_strawberry.title"),
                            Component.translatable("advancements.gemcolony.plant_strawberry.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("strawberry_planted", placedBlock(ModBlocks.STRAWBERRY_BUSH.get()))
                    .save(output, "gemcolony/plant_strawberry");

        }
    }
}
