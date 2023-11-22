package com.github.theredbrain.bamcore.registry;

import com.github.theredbrain.bamcore.BetterAdventureModeCore;
import com.github.theredbrain.bamcore.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EntityRegistry {

    //region Content Blocks
    public static final BlockEntityType<BonfireBlockBlockEntity> BONFIRE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("bonfire"),
            FabricBlockEntityTypeBuilder.create(BonfireBlockBlockEntity::new, BlockRegistry.BONFIRE_BLOCK).build());
    //endregion Content Blocks

    //region Operator Blocks
    public static final BlockEntityType<DungeonControlBlockEntity> DUNGEON_CONTROL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("dungeon_control_block"),
            FabricBlockEntityTypeBuilder.create(DungeonControlBlockEntity::new, BlockRegistry.DUNGEON_CONTROL_BLOCK).build());
    public static final BlockEntityType<HousingBlockBlockEntity> HOUSING_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("housing_block"),
            FabricBlockEntityTypeBuilder.create(HousingBlockBlockEntity::new, BlockRegistry.HOUSING_BLOCK).build());
    public static final BlockEntityType<TeleporterBlockBlockEntity> TELEPORTER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("teleporter_block"),
            FabricBlockEntityTypeBuilder.create(TeleporterBlockBlockEntity::new, BlockRegistry.TELEPORTER_BLOCK).build());
    public static final BlockEntityType<JigsawPlacerBlockBlockEntity> STRUCTURE_PLACER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("structure_placer_block"),
            FabricBlockEntityTypeBuilder.create(JigsawPlacerBlockBlockEntity::new, BlockRegistry.JIGSAW_PLACER_BLOCK).build());
    public static final BlockEntityType<AreaFillerBlockBlockEntity> AREA_FILLER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("area_filler_block"),
            FabricBlockEntityTypeBuilder.create(AreaFillerBlockBlockEntity::new, BlockRegistry.AREA_FILLER_BLOCK).build());
    public static final BlockEntityType<RedstoneTriggerBlockBlockEntity> REDSTONE_TRIGGER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("redstone_trigger_block"),
            FabricBlockEntityTypeBuilder.create(RedstoneTriggerBlockBlockEntity::new, BlockRegistry.REDSTONE_TRIGGER_BLOCK).build());
    public static final BlockEntityType<RelayTriggerBlockBlockEntity> RELAY_TRIGGER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("relay_trigger_block"),
            FabricBlockEntityTypeBuilder.create(RelayTriggerBlockBlockEntity::new, BlockRegistry.RELAY_TRIGGER_BLOCK).build());
    public static final BlockEntityType<DelayTriggerBlockBlockEntity> DELAY_TRIGGER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("delay_trigger_block"),
            FabricBlockEntityTypeBuilder.create(DelayTriggerBlockBlockEntity::new, BlockRegistry.DELAY_TRIGGER_BLOCK).build());
    public static final BlockEntityType<ChunkLoaderBlockBlockEntity> CHUNK_LOADER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            BetterAdventureModeCore.identifier("chunk_loader_block"),
            FabricBlockEntityTypeBuilder.create(ChunkLoaderBlockBlockEntity::new, BlockRegistry.CHUNK_LOADER_BLOCK).build());
    //endregion Operator Blocks

    public static void init() {
    }
}
