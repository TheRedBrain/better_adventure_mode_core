package com.github.theredbrain.bamcore.api.item;

import com.github.theredbrain.bamcore.api.util.BetterAdventureModCoreAttributeModifierUUIDs;
import com.github.theredbrain.bamcore.api.util.BetterAdventureModCoreItemUtils;
import com.github.theredbrain.bamcore.azurelib.BetterAdventureModeRenderProvider;
import com.github.theredbrain.bamcore.client.render.renderer.ArmorTrinketRenderer;
import com.github.theredbrain.bamcore.client.render.renderer.ModeledTrinketRenderer;
import com.github.theredbrain.bamcore.item.ModeledTrinketItem;
import com.github.theredbrain.bamcore.registry.EntityAttributesRegistry;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class ArmorTrinketItem extends ModeledTrinketItem {

    @Nullable
    private String translationKeyBroken;
    private final double armor;
    private final double armorToughness;
    private final double weight;

    public ArmorTrinketItem(double armor, double armorToughness, double weight, Identifier assetSubpath, Settings settings) {
        super(assetSubpath, settings);
        this.armor = armor;
        this.armorToughness = armorToughness;
        this.weight = weight;
    }

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
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getModifiers(stack, slot, entity, uuid);
        String group = slot.inventory().getSlotType().getGroup();
        UUID slotUuid = group.equals("boots") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.BOOTS_SLOT) : group.equals("leggings") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.LEGGINGS_SLOT) : group.equals("gloves") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.GLOVES_SLOT) : group.equals("chest_plates") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.CHESTPLATE_SLOT) : group.equals("shoulders") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.SHOULDERS_SLOT) : group.equals("helmets") ? UUID.fromString(BetterAdventureModCoreAttributeModifierUUIDs.HELMET_SLOT) : null;

        if (slotUuid != null && BetterAdventureModCoreItemUtils.isUsable(stack)) {
            map.put(EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier(slotUuid, "Armor", this.armor, EntityAttributeModifier.Operation.ADDITION));
            map.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                    new EntityAttributeModifier(slotUuid, "Armor Toughness", this.armorToughness, EntityAttributeModifier.Operation.ADDITION));
            map.put(EntityAttributesRegistry.EQUIPMENT_WEIGHT,
                    new EntityAttributeModifier(slotUuid, "Equipment Weight", this.weight, EntityAttributeModifier.Operation.ADDITION));
        }
        return map;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new BetterAdventureModeRenderProvider() {
            private ModeledTrinketRenderer<?> renderer;
            @Override
            public BipedEntityModel<LivingEntity> getGenericTrinketModel(LivingEntity livingEntity, ItemStack itemStack, String slotGroup, String slotName, BipedEntityModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new ArmorTrinketRenderer(ArmorTrinketItem.this.assetSubpath);
                }
                this.renderer.prepForRender(livingEntity, itemStack, slotGroup, slotName, original);
                return this.renderer;
            }
        });
    }
}
