package com.github.theredbrain.betteradventuremode.registry;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import com.github.theredbrain.betteradventuremode.api.effect.AuraStatusEffect;
import com.github.theredbrain.betteradventuremode.api.item.*;
import com.github.theredbrain.betteradventuremode.item.*;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
//import net.spell_engine.api.item.trinket.SpellBookItem;
//import net.spell_engine.api.item.weapon.StaffItem;
//import net.spell_engine.api.spell.SpellContainer;
//import net.spell_engine.internals.SpellRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemRegistry {

    // region Armor
    public static final Item LEATHER_HELMET = registerItem("leather_helmet", new DyeableArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LEATHER_CHESTPLATE = registerItem("leather_chestplate", new DyeableArmorTrinketItem(3, 0, 3, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LEATHER_LEGGINGS = registerItem("leather_leggings", new DyeableArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LEATHER_BOOTS = registerItem("leather_boots", new DyeableArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LEATHER_GLOVES = registerItem("leather_gloves", new DyeableArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LEATHER_SHOULDERS = registerItem("leather_shoulders", new DyeableArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/leather_armor"), new FabricItemSettings().maxDamage(60)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item CHAINMAIL_HELMET = registerItem("chainmail_helmet", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item CHAINMAIL_CHESTPLATE = registerItem("chainmail_chestplate", new ArmorTrinketItem(5, 0, 5, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item CHAINMAIL_LEGGINGS = registerItem("chainmail_leggings", new ArmorTrinketItem(4, 0, 4, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item CHAINMAIL_BOOTS = registerItem("chainmail_boots", new ArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item CHAINMAIL_GLOVES = registerItem("chainmail_gloves", new ArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item CHAINMAIL_SHOULDERS = registerItem("chainmail_shoulders", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/chainmail_armor"), new FabricItemSettings().maxDamage(70)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item IRON_HELMET = registerItem("iron_helmet", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_CHESTPLATE = registerItem("iron_chestplate", new ArmorTrinketItem(6, 0, 6, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_LEGGINGS = registerItem("iron_leggings", new ArmorTrinketItem(5, 0, 5, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_BOOTS = registerItem("iron_boots", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_GLOVES = registerItem("iron_gloves", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_SHOULDERS = registerItem("iron_shoulders", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/iron_armor"), new FabricItemSettings().maxDamage(90)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item DIAMOND_HELMET = registerItem("diamond_helmet", new ArmorTrinketItem(3, 0.025, 3, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_CHESTPLATE = registerItem("diamond_chestplate", new ArmorTrinketItem(8, 0.025, 8, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_LEGGINGS = registerItem("diamond_leggings", new ArmorTrinketItem(6, 0.025, 6, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_BOOTS = registerItem("diamond_boots", new ArmorTrinketItem(3, 0.025, 3, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_GLOVES = registerItem("diamond_gloves", new ArmorTrinketItem(3, 0.025, 3, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_SHOULDERS = registerItem("diamond_shoulders", new ArmorTrinketItem(3, 0.025, 3, BetterAdventureMode.identifier("model/armor/diamond_armor"), new FabricItemSettings().maxDamage(40)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item GOLDEN_HELMET = registerItem("golden_helmet", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_CHESTPLATE = registerItem("golden_chestplate", new ArmorTrinketItem(5, 0, 5, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_LEGGINGS = registerItem("golden_leggings", new ArmorTrinketItem(3, 0, 3, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_BOOTS = registerItem("golden_boots", new ArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_GLOVES = registerItem("golden_gloves", new ArmorTrinketItem(1, 0, 1, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_SHOULDERS = registerItem("golden_shoulders", new ArmorTrinketItem(2, 0, 2, BetterAdventureMode.identifier("model/armor/golden_armor"), new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item NETHERITE_HELMET = registerItem("netherite_helmet", new ArmorTrinketItem(3, 0.05, 3, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_CHESTPLATE = registerItem("netherite_chestplate", new ArmorTrinketItem(8, 0.05, 8, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_LEGGINGS = registerItem("netherite_leggings", new ArmorTrinketItem(6, 0.05, 6, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_BOOTS = registerItem("netherite_boots", new ArmorTrinketItem(3, 0.05, 3, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_GLOVES = registerItem("netherite_gloves", new ArmorTrinketItem(3, 0.05, 3, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_SHOULDERS = registerItem("netherite_shoulders", new ArmorTrinketItem(3, 0.05, 3, BetterAdventureMode.identifier("model/armor/netherite_armor"), new FabricItemSettings().maxDamage(120)), ItemGroupRegistry.BAM_EQUIPMENT);
    //endregion Armor

    //region Accessories
    // belts
    public static final Item MANA_REGENERATION_BELT = registerItem("mana_regeneration_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DEPTH_STRIDER_BELT = registerItem("depth_strider_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item LOOTING_BELT = registerItem("looting_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item UNBREAKING_BELT = registerItem("unbreaking_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item FEATHER_FALLING_BELT = registerItem("feather_falling_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);

    // necklaces
    public static final Item TELEPORT_HOME_NECKLACE = registerItem("teleport_home_necklace", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/necklace_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item HEALTH_REGENERATION_AURA_NECKLACE = registerItem("health_regeneration_aura_necklace", new AuraGrantingNecklaceTrinketItem((AuraStatusEffect) StatusEffectsRegistry.HEALTH_REGENERATION_AURA_EFFECT, BetterAdventureMode.identifier("model/accessory/necklace_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);

    // rings
    public static final Item TWO_HEALTH_REGENERATION_RING = registerItem("two_health_regeneration_ring", new ModifyEntityAttributeRingItem(EntityAttributesRegistry.HEALTH_REGENERATION, "health_regeneration", 2, EntityAttributeModifier.Operation.ADDITION, BetterAdventureMode.identifier("model/accessory/ring_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item THREE_SPELL_SLOT_RING = registerItem("three_spell_slot_ring", new ModifyEntityAttributeRingItem(EntityAttributesRegistry.ACTIVE_SPELL_SLOT_AMOUNT, "active_spell_slot_amount", 3, EntityAttributeModifier.Operation.ADDITION, BetterAdventureMode.identifier("model/accessory/ring_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item ARMOR_TOUGHNESS_RING = registerItem("armor_toughness_ring", new ModifyEntityAttributeRingItem(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "armor_toughness", 0.25, EntityAttributeModifier.Operation.ADDITION, BetterAdventureMode.identifier("model/accessory/ring_basic"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT);

    // elemental spell books
    // TODO SpellEngine
    public static final Item FIREBALL_SPELL_BOOK = registerSpellContainerItem("fireball_spell_book", new MultiSlotSpellBookItem(-1, BetterAdventureMode.identifier("fireball"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:fireball"));
    public static final Item DRAGONS_BREATH_SPELL_BOOK = registerSpellContainerItem("dragons_breath_spell_book", new TrinketItem/*SpellBookItem*/(/*BetterAdventureMode.identifier("dragons_breath"), */new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:dragons_breath"));
    public static final Item RING_OF_FIRE_SPELL_BOOK = registerSpellContainerItem("ring_of_fire_spell_book", new MultiSlotSpellBookItem(-1, BetterAdventureMode.identifier("ring_of_fire"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:ring_of_fire"));
    public static final Item FROST_SHARDS_SPELL_BOOK = registerSpellContainerItem("frost_shards_spell_book", new TrinketItem/*SpellBookItem*/(/*BetterAdventureMode.identifier("frost_shards"), */new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:frost_shards"));
    public static final Item GLACIER_SPELL_BOOK = registerSpellContainerItem("glacier_spell_book", new MultiSlotSpellBookItem(-1, BetterAdventureMode.identifier("glacier"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:glacier"));
    public static final Item COLD_SNAP_SPELL_BOOK = registerSpellContainerItem("cold_snap_spell_book", new TrinketItem/*SpellBookItem*/(/*BetterAdventureMode.identifier("cold_snap"), */new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:cold_snap"));
    public static final Item LIGHTNING_STRIKE_SPELL_BOOK = registerSpellContainerItem("lightning_strike_spell_book", new MultiSlotSpellBookItem(-1, BetterAdventureMode.identifier("lightning_strike"), new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:lightning_strike"));
    public static final Item WATER_SPRAY_SPELL_BOOK = registerSpellContainerItem("water_spray_spell_book", new TrinketItem/*SpellBookItem*/(/*BetterAdventureMode.identifier("water_spray"), */new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:water_spray"));

    // divine spell books
    // TODO SpellEngine
    public static final Item SINGLE_TARGET_HEAL_SPELL_BOOK = registerSpellContainerItem("single_target_heal_spell_book", new TrinketItem/*SpellBookItem*/(/*BetterAdventureMode.identifier("single_target_heal"), */new FabricItemSettings().maxCount(1)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:frost_nova"));
    //endregion Accessories

    //region Hotbar Items
    // spell scrolls
    public static final Item FIREBALL_SPELL_SCROLL = registerItem("fireball_spell_scroll", new Item(new FabricItemSettings().maxCount(16)), ItemGroupRegistry.BAM_EQUIPMENT);

    // food
    public static final Item SWEET_BERRIES = registerItem("sweet_berries", new AdventureFoodConsumable(new FabricItemSettings().maxCount(16).food(AdventureFoodComponents.SWEET_BERRIES)), ItemGroupRegistry.BAM_FOOD);
    public static final Item BROWN_MUSHROOM = registerItem("brown_mushroom", new AdventureFoodConsumable(new FabricItemSettings().maxCount(16).food(AdventureFoodComponents.BROWN_MUSHROOM)), ItemGroupRegistry.BAM_FOOD);
    public static final Item RED_MUSHROOM = registerItem("red_mushroom", new AdventureFoodConsumable(new FabricItemSettings().maxCount(16).food(AdventureFoodComponents.RED_MUSHROOM)), ItemGroupRegistry.BAM_FOOD);
    public static final Item GLOW_BERRIES = registerItem("glow_berries", new AdventureFoodConsumable(new FabricItemSettings().maxCount(16).food(AdventureFoodComponents.GLOW_BERRIES)), ItemGroupRegistry.BAM_FOOD);
    //endregion Hotbar Items
    
    // weapons
    // TODO SpellEngine
    public static final Item ELEMENTAL_FIRE_WAND = registerFilteredSpellProxyItem("elemental_fire_wand", new ToolItem/*StaffItem*/(ToolMaterials.WOOD, new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:burning_touch"), "betteradventuremode:elemental_fire_wand_pool");
    public static final Item ELEMENTAL_ICE_WAND = registerFilteredSpellProxyItem("elemental_ice_wand", new ToolItem/*StaffItem*/(ToolMaterials.WOOD, new FabricItemSettings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT, List.of("betteradventuremode:chilling_touch"), "betteradventuremode:elemental_ice_wand_pool");

//    public static final Item ZWEIHANDER = registerItem("zweihander", new BetterAdventureMode_BasicWeaponItem(DamageTypes.PLAYER_ATTACK, DamageTypes.PLAYER_ATTACK,7, -3.5F, 5, 5,  new Item.Settings().maxDamage(336)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item WOODEN_SWORD = registerItem("wooden_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,3, -2.4F, 1, 1,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item STONE_SWORD = registerItem("stone_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,4, -2.4F, 1, 3,  new Item.Settings().maxDamage(100)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_SWORD = registerItem("golden_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,3, -2.4F, 1, 4,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_SWORD = registerItem("iron_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,5, -2.4F, 1, 3,  new Item.Settings().maxDamage(200)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_SWORD = registerItem("diamond_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,6, -2.4F, 2, 3,  new Item.Settings().maxDamage(250)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_SWORD = registerItem("netherite_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,7, -2.4F, 2, 3,  new Item.Settings().maxDamage(300)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item WOODEN_PICKAXE = registerItem("wooden_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,3, -2.8F, 1, 1,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item STONE_PICKAXE = registerItem("stone_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,4, -2.8F, 1, 3,  new Item.Settings().maxDamage(100)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_PICKAXE = registerItem("golden_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,3, -2.8F, 1, 4,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_PICKAXE = registerItem("iron_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,5, -2.8F, 1, 3,  new Item.Settings().maxDamage(200)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_PICKAXE = registerItem("diamond_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,6, -2.8F, 2, 3,  new Item.Settings().maxDamage(250)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_PICKAXE = registerItem("netherite_pickaxe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE,7, -2.8F, 2, 3,  new Item.Settings().maxDamage(300)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item WOODEN_AXE = registerItem("wooden_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,3, -3.0F, 1, 1,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item STONE_AXE = registerItem("stone_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,4, -3.0F, 1, 3,  new Item.Settings().maxDamage(100)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item GOLDEN_AXE = registerItem("golden_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,3, -3.0F, 1, 4,  new Item.Settings().maxDamage(50)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item IRON_AXE = registerItem("iron_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,5, -3.0F, 1, 3,  new Item.Settings().maxDamage(200)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item DIAMOND_AXE = registerItem("diamond_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,6, -3.0F, 2, 3,  new Item.Settings().maxDamage(250)), ItemGroupRegistry.BAM_EQUIPMENT);
    public static final Item NETHERITE_AXE = registerItem("netherite_axe", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE,7, -3.0F, 2, 3,  new Item.Settings().maxDamage(300)), ItemGroupRegistry.BAM_EQUIPMENT);

    public static final Item DEFAULT_EMPTY_HAND_WEAPON = registerItem("default_empty_hand_weapon", new EmptyHandWeapon(1, -3.0F, 1, new Item.Settings()), null);

    // test items
    public static final Item TEST_NECKLACE = registerItem("test_necklace", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/necklace_basic"), new FabricItemSettings().maxCount(1)), ItemGroups.OPERATOR);
    public static final Item BLOCK_POSITION_DISTANCE_METER = registerItem("block_position_distance_meter", new BlockPositionDistanceMeterItem(new FabricItemSettings().maxCount(1)), ItemGroupRegistry.SCRIPT_BLOCKS);

    // TODO SpellEngine
    public static final Item TEST_WAND = registerFilteredSpellProxyItem("test_wand", new ToolItem/*StaffItem*/(ToolMaterials.WOOD, new FabricItemSettings().maxDamage(50)), ItemGroups.OPERATOR, List.of(), "betteradventuremode:test_wand_pool");
    public static final Item TEST_BELT = registerItem("test_belt", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/belt_basic"), new FabricItemSettings().maxCount(1)), ItemGroups.OPERATOR);
    public static final Item TEST_RING = registerItem("test_ring", new AccessoryTrinketItem(BetterAdventureMode.identifier("model/accessory/ring_basic"), new FabricItemSettings().maxCount(1)), ItemGroups.OPERATOR);
    public static final Item MANA_TEST_BELT = registerItem("mana_test_belt", new ManaTestBeltItem(new FabricItemSettings().maxCount(1)), ItemGroups.OPERATOR);
    public static final Item TEST_BUCKLER = registerItem("test_buckler", new BasicShieldItem(2, 0, 0, 0, 0.2, true, 3.0, 2, new FabricItemSettings().maxDamage(336)), ItemGroups.OPERATOR);
    public static final Item TEST_NORMAL_SHIELD = registerItem("test_normal_shield", new BasicShieldItem(3, 0, 0, 0, 0.5, true, 1.5, 3, new FabricItemSettings().maxDamage(336)), ItemGroups.OPERATOR);
    public static final Item TEST_TOWER_SHIELD = registerItem("test_tower_shield", new BasicShieldItem(5, 0, 0, 0, 1.0, false, 1.0, 5, new FabricItemSettings().maxDamage(336)), ItemGroups.OPERATOR);
    public static final Item TEST_SWORD = registerItem("test_sword", new BasicWeaponItem(DamageTypesRegistry.PLAYER_SLASHING_DAMAGE_TYPE, DamageTypesRegistry.PLAYER_PIERCING_DAMAGE_TYPE, 4, -3.0F, 3, 2, new FabricItemSettings().maxDamage(336)), ItemGroups.OPERATOR);
    public static final Item TEST_AURA_NECKLACE = registerItem("test_aura_necklace", new AuraGrantingNecklaceTrinketItem((AuraStatusEffect) StatusEffectsRegistry.TEST_AURA_EFFECT, BetterAdventureMode.identifier("model/accessory/invisible"), new FabricItemSettings().maxCount(1)), ItemGroups.OPERATOR);

    public static final Item MANNEQUIN = registerItem("mannequin", new MannequinItem(new FabricItemSettings()), ItemGroupRegistry.BAM_BLOCK);
//    public static final Item MANNEQUIN_SLIM = registerItem("mannequin_slim", new MannequinItem(new FabricItemSettings(), true), ItemGroupRegistry.BAM_BLOCK);

    private static Item registerItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup) {

        if (itemGroup != null) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
                content.add(item);
            });
        }
        return Registry.register(Registries.ITEM, BetterAdventureMode.identifier(name), item);
    }

    private static Item registerSpellContainerItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup, List<String> spells) {

        // TODO SpellEngine
//        SpellContainer container = new SpellContainer(false, null, 0, spells);
//        SpellRegistry.book_containers.put(BetterAdventureMode.identifier(name), container);

        if (itemGroup != null) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
                content.add(item);
            });
        }
        return Registry.register(Registries.ITEM, BetterAdventureMode.identifier(name), item);
    }

    private static Item registerFilteredSpellProxyItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup, List<String> spells, String proxyPool) {

        // TODO SpellEngine
//        SpellContainer container = BetterAdventureModCoreItemUtils.setProxyPool(new SpellContainer(true, null, 0, spells), proxyPool);
//        SpellRegistry.book_containers.put(BetterAdventureMode.identifier(name), container);

        if (itemGroup != null) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
                content.add(item);
            });
        }
        return Registry.register(Registries.ITEM, BetterAdventureMode.identifier(name), item);
    }

    public static void init() {
    }
}