package com.fodk.gemcolony.worldgen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;
import java.util.function.Function;

public class BuriedSinglePoolElement extends SinglePoolElement {

    private final int yOffset;

    public static final MapCodec<BuriedSinglePoolElement> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    templateCodec(),
                    processorsCodec(),
                    projectionCodec(),
                    overrideLiquidSettingsCodec(),
                    Codec.INT.fieldOf("y_offset").forGetter(element -> element.yOffset))
                    .apply(i, BuriedSinglePoolElement::new));

    public BuriedSinglePoolElement(Either<Identifier, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, int yOffset) {
        super(
                template,
                processors,
                projection,
                overrideLiquidSettings
        );
        this.yOffset = yOffset;
    }

    public static Function<StructureTemplatePool.Projection, BuriedSinglePoolElement> buried(String location, Holder<StructureProcessorList> processors, int yOffset) {
        return projection -> new BuriedSinglePoolElement(
                Either.left(Identifier.parse(location)),
                processors,
                projection,
                Optional.empty(),
                yOffset
        );
    }

    @Override
    public int getGroundLevelDelta() {
        return 0;
    }

    @Override
    public BoundingBox getBoundingBox(StructureTemplateManager structureTemplateManager, BlockPos position, Rotation rotation) {
        BoundingBox box = super.getBoundingBox(structureTemplateManager, position, rotation);
        return box.moved(0, this.yOffset, 0);
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return ModStructurePoolElementTypes.BURIED_SINGLE.get();
    }
}
