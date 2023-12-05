package com.github.theredbrain.bamcore.network.packet;

import com.github.theredbrain.bamcore.BetterAdventureModeCore;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class BetterAdventureModeCoreServerPacket {

    public static final Identifier SWAP_HAND_ITEMS_PACKET = BetterAdventureModeCore.identifier("swap_hand_items");
    public static final Identifier SWAPPED_HAND_ITEMS_PACKET = BetterAdventureModeCore.identifier("swapped_hand_items");
    public static final Identifier SHEATHE_WEAPONS_PACKET = BetterAdventureModeCore.identifier("sheathe_weapons");
    public static final Identifier TWO_HAND_MAIN_WEAPON_PACKET = BetterAdventureModeCore.identifier("two_hand_main_weapon");
    public static final Identifier TOGGLE_NECKLACE_ABILITY_PACKET = BetterAdventureModeCore.identifier("toggle_necklace_ability_weapon");
    public static final Identifier ATTACK_STAMINA_COST_PACKET = BetterAdventureModeCore.identifier("attack_stamina_cost");
    public static final Identifier CANCEL_ATTACK_PACKET = BetterAdventureModeCore.identifier("attack_stamina_cost");
    public static final Identifier ADD_STATUS_EFFECT_PACKET = BetterAdventureModeCore.identifier("add_status_effect");
//    public static final Identifier SHEATHED_WEAPONS_PACKET = RPGMod.identifier("sheathed_weapons"); // TODO if weapon sheathing is not visible in multiplayer

    public static final Identifier SYNC_CONFIG = BetterAdventureModeCore.identifier("sync_config");
    public static final Identifier SYNC_PLAYER_HOUSES = BetterAdventureModeCore.identifier("sync_player_houses");
    public static final Identifier SYNC_PLAYER_DUNGEONS = BetterAdventureModeCore.identifier("sync_player_dungeons");
    public static final Identifier UPDATE_HOUSING_BLOCK_ADVENTURE = BetterAdventureModeCore.identifier("update_housing_block_adventure");
    public static final Identifier UPDATE_HOUSING_BLOCK_CREATIVE = BetterAdventureModeCore.identifier("update_housing_block_creative");
    public static final Identifier SET_HOUSING_OWNER_BLOCK = BetterAdventureModeCore.identifier("set_housing_owner_block");
    public static final Identifier RESET_HOUSE_HOUSING_BLOCK = BetterAdventureModeCore.identifier("reset_house_housing_block");
    public static final Identifier UPDATE_TELEPORTER_BLOCK = BetterAdventureModeCore.identifier("update_teleporter_block");
    public static final Identifier UPDATE_AREA_FILLER_BLOCK = BetterAdventureModeCore.identifier("update_area_filler_block");
    public static final Identifier UPDATE_JIGSAW_PLACER_BLOCK = BetterAdventureModeCore.identifier("update_jigsaw_placer_block");
    public static final Identifier UPDATE_REDSTONE_TRIGGER_BLOCK = BetterAdventureModeCore.identifier("update_redstone_trigger_block");
    public static final Identifier UPDATE_RELAY_TRIGGER_BLOCK = BetterAdventureModeCore.identifier("update_relay_trigger_block");
    public static final Identifier UPDATE_DELAY_TRIGGER_BLOCK = BetterAdventureModeCore.identifier("update_delay_trigger_block");
    public static final Identifier UPDATE_CHUNK_LOADER_BLOCK = BetterAdventureModeCore.identifier("update_chunk_loader_block");
    public static final Identifier TELEPORT_FROM_TELEPORTER_BLOCK = BetterAdventureModeCore.identifier("teleport_from_teleporter_block");
    public static final Identifier REGENERATE_DIMENSION_FROM_TELEPORTER_BLOCK = BetterAdventureModeCore.identifier("regenerate_dimension_from_teleporter_block");

    public static void init() {
        SwapHandItemsPacketReceiver swapHandItemsPacketReceiver = new SwapHandItemsPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.SWAP_HAND_ITEMS_PACKET, swapHandItemsPacketReceiver);

        SheatheWeaponsPacketReceiver sheatheWeaponsPacketReceiver = new SheatheWeaponsPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.SHEATHE_WEAPONS_PACKET, sheatheWeaponsPacketReceiver);

        TwoHandMainWeaponPacketReceiver twoHandMainWeaponPacketReceiver = new TwoHandMainWeaponPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.TWO_HAND_MAIN_WEAPON_PACKET, twoHandMainWeaponPacketReceiver);

        ToggleNecklaceAbilityPacketReceiver toggleNecklaceAbilityPacketReceiver = new ToggleNecklaceAbilityPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.TOGGLE_NECKLACE_ABILITY_PACKET, toggleNecklaceAbilityPacketReceiver);

        AttackStaminaCostPacketReceiver attackStaminaCostPacketReceiver = new AttackStaminaCostPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.ATTACK_STAMINA_COST_PACKET, attackStaminaCostPacketReceiver);

        AddStatusEffectPacketReceiver addStatusEffectPacketReceiver = new AddStatusEffectPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.ADD_STATUS_EFFECT_PACKET, addStatusEffectPacketReceiver);


        UpdateHousingBlockAdventurePacketReceiver updateHousingBlockAdventurePacketReceiver = new UpdateHousingBlockAdventurePacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_HOUSING_BLOCK_ADVENTURE, updateHousingBlockAdventurePacketReceiver);

        UpdateHousingBlockCreativePacketReceiver updateHousingBlockCreativePacketReceiver = new UpdateHousingBlockCreativePacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_HOUSING_BLOCK_CREATIVE, updateHousingBlockCreativePacketReceiver);

        SetHousingBlockOwnerPacketReceiver setHousingBlockOwnerPacketReceiver = new SetHousingBlockOwnerPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.SET_HOUSING_OWNER_BLOCK, setHousingBlockOwnerPacketReceiver);

        ResetHouseHousingBlockPacketReceiver resetHouseHousingBlockPacketReceiver = new ResetHouseHousingBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.RESET_HOUSE_HOUSING_BLOCK, resetHouseHousingBlockPacketReceiver);

        UpdateTeleporterBlockPacketReceiver updateTeleporterBlockPacketReceiver = new UpdateTeleporterBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_TELEPORTER_BLOCK, updateTeleporterBlockPacketReceiver);

        UpdateAreaFillerBlockPacketReceiver updateAreaFillerBlockPacketReceiver = new UpdateAreaFillerBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_AREA_FILLER_BLOCK, updateAreaFillerBlockPacketReceiver);

        UpdateJigsawPlacerBlockPacketReceiver updateJigsawPlacerBlockPacketReceiver = new UpdateJigsawPlacerBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_JIGSAW_PLACER_BLOCK, updateJigsawPlacerBlockPacketReceiver);

        UpdateRedstoneTriggerBlockPacketReceiver updateRedstoneTriggerBlockPacketReceiver = new UpdateRedstoneTriggerBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_REDSTONE_TRIGGER_BLOCK, updateRedstoneTriggerBlockPacketReceiver);

        UpdateRelayTriggerBlockPacketReceiver updateRelayTriggerBlockPacketReceiver = new UpdateRelayTriggerBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_RELAY_TRIGGER_BLOCK, updateRelayTriggerBlockPacketReceiver);

        UpdateDelayTriggerBlockPacketReceiver updateDelayTriggerBlockPacketReceiver = new UpdateDelayTriggerBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_DELAY_TRIGGER_BLOCK, updateDelayTriggerBlockPacketReceiver);

        UpdateChunkLoaderBlockPacketReceiver updateChunkLoaderBlockPacketReceiver = new UpdateChunkLoaderBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.UPDATE_CHUNK_LOADER_BLOCK, updateChunkLoaderBlockPacketReceiver);

        TeleportFromTeleporterBlockPacketReceiver teleportFromTeleporterBlockPacketReceiver = new TeleportFromTeleporterBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.TELEPORT_FROM_TELEPORTER_BLOCK, teleportFromTeleporterBlockPacketReceiver);

        RegenerateDimensionFromTeleporterBlockPacketReceiver regenerateDimensionFromTeleporterBlockPacketReceiver = new RegenerateDimensionFromTeleporterBlockPacketReceiver();
        ServerPlayNetworking.registerGlobalReceiver(BetterAdventureModeCoreServerPacket.REGENERATE_DIMENSION_FROM_TELEPORTER_BLOCK, regenerateDimensionFromTeleporterBlockPacketReceiver);
    }
}