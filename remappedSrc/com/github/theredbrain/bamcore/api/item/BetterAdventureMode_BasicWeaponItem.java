package com.github.theredbrain.bamcore.api.item;

import com.github.theredbrain.bamcore.api.util.BetterAdventureModCoreAttributeModifierUUIDs;
import com.github.theredbrain.bamcore.api.util.BetterAdventureModCoreItemUtils;
import com.github.theredbrain.bamcore.registry.EntityAttributesRegistry;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BetterAdventureMode_BasicWeaponItem extends Item {

    private final RegistryKey<DamageType> damageTypeRegistryKey;
    @Nullable
    private final RegistryKey<DamageType> twoHandedDamageTypeRegistryKey;
    private final float attackDamage;
    private final float attackSpeed;
    private final int staminaCost;
    private final float weight;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    @Nullable
    private String translationKeyBroken;

    public BetterAdventureMode_BasicWeaponItem(RegistryKey<DamageType> damageTypeRegistryKey, @Nullable RegistryKey<DamageType> twoHandedDamageTypeRegistryKey, float attackDamage, float attackSpeed, int staminaCost, float weight, Settings settings) {
        super(settings);
        this.damageTypeRegistryKey = damageTypeRegistryKey;
        this.twoHandedDamageTypeRegistryKey = twoHandedDamageTypeRegistryKey;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.staminaCost = staminaCost;
        this.weight = weight;
        this.attributeModifiers = buildModifiers();
    }

    /**
     * {@return whether this items can lose durability}
     */
    @Override
    public boolean isDamageable() {
        return this.getMaxDamage() > 1;
    }

    /**
     * Gets or creates the translation key of this item when it is not protecting.
     */
    private String getOrCreateTranslationKeyBroken() {
        if (this.translationKeyBroken == null) {
            this.translationKeyBroken = Util.createTranslationKey("item", new Identifier(Registries.ITEM.getId(this).getNamespace() + ":" + Registries.ITEM.getId(this).getPath() + "_broken"));
        }
        return this.translationKeyBroken;
    }

    /**
     * Gets the translation key of this item using the provided item stack for context.
     */
    @Override
    public String getTranslationKey(ItemStack stack) {
        return BetterAdventureModCoreItemUtils.isUsable(stack) ? this.getTranslationKey() : this.getOrCreateTranslationKeyBroken();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (BetterAdventureModCoreItemUtils.isUsable(stack)) {
            stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            return true;
        }
        return false;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (BetterAdventureModCoreItemUtils.isUsable(stack) && !world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            return true;
        }
        return false;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    protected Multimap<EntityAttribute, EntityAttributeModifier> buildModifiers() {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.MAIN_HAND_SLOT), "generic_attack_damage", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.MAIN_HAND_SLOT), "generic_attack_speed", (double)this.attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributesRegistry.EQUIPMENT_WEIGHT,
                new EntityAttributeModifier(UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.MAIN_HAND_SLOT), "equipment_weight", (double)this.weight, EntityAttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public int getStaminaCost() {
        return staminaCost;
    }

    public RegistryKey<DamageType> getDamageTypeRegistryKey(boolean twoHanded) {
        if (twoHanded && this.twoHandedDamageTypeRegistryKey != null) {
            return this.twoHandedDamageTypeRegistryKey;
        }
        return damageTypeRegistryKey;
    }
}
