package com.github.theredbrain.betteradventuremode.mixin.entity;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import com.github.theredbrain.betteradventuremode.BetterAdventureModeClient;
import com.github.theredbrain.betteradventuremode.api.item.BasicShieldItem;
import com.github.theredbrain.betteradventuremode.registry.EntityAttributesRegistry;
import com.github.theredbrain.betteradventuremode.registry.StatusEffectsRegistry;
import com.github.theredbrain.betteradventuremode.entity.DuckLivingEntityMixin;
import com.github.theredbrain.betteradventuremode.registry.GameRulesRegistry;
import com.github.theredbrain.betteradventuremode.registry.Tags;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(value = LivingEntity.class, priority = 950)
public abstract class LivingEntityMixin extends Entity implements DuckLivingEntityMixin {

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    private @Nullable DamageSource lastDamageSource;
    @Shadow
    private long lastDamageTime;

    @Shadow
    protected abstract void playHurtSound(DamageSource source);

    @Shadow
    public abstract void onDeath(DamageSource damageSource);

    @Shadow
    protected abstract @Nullable SoundEvent getDeathSound();

    @Shadow
    public abstract boolean isDead();

    @Shadow
    protected abstract boolean tryUseTotem(DamageSource source);

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    public abstract float getSoundPitch();

    @Shadow
    public abstract void tiltScreen(double deltaX, double deltaZ);

    @Shadow
    public abstract void takeKnockback(double strength, double x, double z);

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract boolean isSleeping();

    @Shadow
    public abstract void wakeUp();

    @Shadow
    protected int despawnCounter;
    @Shadow
    @Final
    public LimbAnimator limbAnimator;
    @Shadow
    protected float lastDamageTaken;
    @Shadow
    public int hurtTime;
    @Shadow
    public int maxHurtTime;

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow
    public abstract void damageHelmet(DamageSource source, float amount);

    @Shadow
    protected int playerHitTimer;
    @Shadow
    @Nullable
    protected PlayerEntity attackingPlayer;

    @Shadow
    public abstract void setAttacker(@Nullable LivingEntity attacker);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract DamageTracker getDamageTracker();

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract @Nullable StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    public abstract void damageArmor(DamageSource source, float amount);

    @Shadow
    public abstract int getArmor();

    @Shadow
    public abstract double getAttributeValue(EntityAttribute attribute);

    @Shadow
    public abstract ItemStack getOffHandStack();

    @Shadow
    public abstract boolean isBlocking();

    @Shadow
    public abstract boolean blockedByShield(DamageSource source);

    @Shadow
    public abstract void stopUsingItem();

    @Shadow
    public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

    @Unique
    private int healthTickTimer = 0;
    @Unique
    private int staminaTickTimer = 0;
    @Unique
    private int manaTickTimer = 0;
    @Unique
    private int staminaRegenerationDelayTimer = 0;
    @Unique
    private boolean delayStaminaRegeneration = false;
    @Unique
    private int blockingTime = 0;

    @Unique
    private static final TrackedData<Float> BLEEDING_BUILD_UP = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> BURN_BUILD_UP = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> FREEZE_BUILD_UP = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> MANA = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> POISE = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> POISON_BUILD_UP = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> SHOCK_BUILD_UP = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Unique
    private static final TrackedData<Float> STAMINA = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void betteradventuremode$initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(BLEEDING_BUILD_UP, 0.0F);
        this.dataTracker.startTracking(BURN_BUILD_UP, 0.0F);
        this.dataTracker.startTracking(FREEZE_BUILD_UP, 0.0F);
        this.dataTracker.startTracking(MANA, 0.0F);
        this.dataTracker.startTracking(POISE, 0.0F);
        this.dataTracker.startTracking(POISON_BUILD_UP, 0.0F);
        this.dataTracker.startTracking(SHOCK_BUILD_UP, 0.0F);
        this.dataTracker.startTracking(STAMINA, 20.0F);

    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"), cancellable = true)
    private static void betteradventuremode$createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .add(EntityAttributesRegistry.HEALTH_REGENERATION, 0.0F) // TODO balance
                .add(EntityAttributesRegistry.MANA_REGENERATION, 0.0F) // TODO balance
                .add(EntityAttributesRegistry.STAMINA_REGENERATION, 1.0F) // TODO balance
                .add(EntityAttributesRegistry.MAX_MANA, 0.0F) // TODO balance
                .add(EntityAttributesRegistry.MAX_STAMINA, 20.0F) // TODO balance
                .add(EntityAttributesRegistry.ADDITIONAL_BASHING_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_PIERCING_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_SLASHING_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_FIRE_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_FROST_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_LIGHTNING_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.ADDITIONAL_POISON_DAMAGE, 0.0F)
                .add(EntityAttributesRegistry.INCREASED_BASHING_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_PIERCING_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_SLASHING_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_FIRE_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_FROST_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_LIGHTNING_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.INCREASED_POISON_DAMAGE, 1.0F)
                .add(EntityAttributesRegistry.POISON_RESISTANCE, 0.0F)
                .add(EntityAttributesRegistry.FIRE_RESISTANCE, 0.0F)
                .add(EntityAttributesRegistry.FROST_RESISTANCE, 0.0F)
                .add(EntityAttributesRegistry.LIGHTNING_RESISTANCE, 0.0F)
        );
    }

    /**
     * @author TheRedBrain
     * @reason inject gamerule destroyDroppedItemsOnDeath into Trinkets drop logic
     */
    @Overwrite
    public void dropInventory() {
        LivingEntity entity = (LivingEntity) (Object) this;

        boolean keepInv = entity.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY);

        boolean destroyDroppedItems;
        if (entity.getServer() != null && entity instanceof PlayerEntity) {
            destroyDroppedItems = entity.getServer().getGameRules().getBoolean(GameRulesRegistry.DESTROY_DROPPED_ITEMS_ON_DEATH);
        } else {
            destroyDroppedItems = false;
        }
        TrinketsApi.getTrinketComponent(entity).ifPresent(trinkets -> trinkets.forEach((ref, stack) -> {
            if (stack.isEmpty()) {
                return;
            }

            TrinketEnums.DropRule dropRule = TrinketsApi.getTrinket(stack.getItem()).getDropRule(stack, ref, entity);

            dropRule = TrinketDropCallback.EVENT.invoker().drop(dropRule, stack, ref, entity);

            TrinketInventory inventory = ref.inventory();

            if (dropRule == TrinketEnums.DropRule.DEFAULT) {
                dropRule = inventory.getSlotType().getDropRule();
            }

            if (dropRule == TrinketEnums.DropRule.DEFAULT) {
                if (keepInv && entity.getType() == EntityType.PLAYER) {
                    dropRule = TrinketEnums.DropRule.KEEP;
                } else {
                    if (EnchantmentHelper.hasVanishingCurse(stack) || destroyDroppedItems) {
                        dropRule = TrinketEnums.DropRule.DESTROY;
                    } else {
                        dropRule = TrinketEnums.DropRule.DROP;
                    }
                }
            }

            switch (dropRule) {
                case DROP:
                    betteradventuremode$dropFromEntity(stack);
                    // Fallthrough
                case DESTROY:
                    inventory.setStack(ref.index(), ItemStack.EMPTY);
                    break;
                default:
                    break;
            }
        }));
    }

    /**
     * @author TheRedBrain
     * @reason
     */
    @Overwrite
    public void heal(float amount) {
        float f = this.getHealth();
        if (f > 0.0f) {
            this.setHealth(f + amount);
        }
        if (amount < 0) {
            this.healthTickTimer = 0;
        }
    }

    @Unique
    private void betteradventuremode$dropFromEntity(ItemStack stack) {
        ItemEntity entity = dropStack(stack);
        // Mimic player drop behavior for only players
        if (entity != null && ((Entity) this) instanceof PlayerEntity) {
            entity.setPos(entity.getX(), this.getEyeY() - 0.3, entity.getZ());
            entity.setPickupDelay(40);
            float magnitude = this.random.nextFloat() * 0.5f;
            float angle = this.random.nextFloat() * ((float) Math.PI * 2);
            entity.setVelocity(-MathHelper.sin(angle) * magnitude, 0.2f, MathHelper.cos(angle) * magnitude);
        }
    }

    /**
     * @author TheRedBrain
     * @reason
     */
    @Overwrite
    public boolean damage(DamageSource source, float amount) {
        boolean bl3;
        Entity entity2;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.getWorld().isClient) {
            return false;
        }
        if (this.isDead()) {
            return false;
        }
        if (source.isIn(DamageTypeTags.IS_FIRE) && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            return false;
        }
        if (this.isSleeping() && !this.getWorld().isClient) {
            this.wakeUp();
        }
        this.despawnCounter = 0;
        float f = amount;
        boolean bl = false;

        // rework shields
//        float g = 0.0f;
//        if (amount > 0.0f && this.blockedByShield(source)) {
//            Entity entity;
//            this.damageShield(amount);
//            g = amount;
//            amount = 0.0f;
//            if (!source.isIn(DamageTypeTags.IS_PROJECTILE) && (entity = source.getSource()) instanceof LivingEntity) {
//                LivingEntity livingEntity = (LivingEntity)entity;
//                this.takeShieldHit(livingEntity);
//            }
//            bl = true;
//        }


        if (source.isIn(DamageTypeTags.IS_FREEZING) && this.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
            amount *= 5.0f;
        }
        this.limbAnimator.setSpeed(1.5f);
        boolean bl2 = true;

        if ((float) this.timeUntilRegen > 10.0f && !source.isIn(DamageTypeTags.BYPASSES_COOLDOWN)) {
            if (amount <= this.lastDamageTime) {
                return false;
            }
            this.applyDamage(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            bl2 = false;
        } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.applyDamage(source, amount);
            this.hurtTime = this.maxHurtTime = 10;
        }
        if (source.isIn(DamageTypeTags.DAMAGES_HELMET) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            this.damageHelmet(source, amount);
            amount *= 0.75f;
        }
        if ((entity2 = source.getAttacker()) != null) {
            WolfEntity wolfEntity;
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingEntity2 = (LivingEntity) entity2;
                if (!source.isIn(DamageTypeTags.NO_ANGER)) {
                    this.setAttacker(livingEntity2);
                }
            }
            if (entity2 instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity2;
                this.playerHitTimer = 100;
                this.attackingPlayer = playerEntity;
            } else if (entity2 instanceof WolfEntity && (wolfEntity = (WolfEntity) entity2).isTamed()) {
                PlayerEntity playerEntity2;
                this.playerHitTimer = 100;
                LivingEntity livingEntity = wolfEntity.getOwner();
                this.attackingPlayer = livingEntity instanceof PlayerEntity ? (playerEntity2 = (PlayerEntity) livingEntity) : null;
            }
        }
        if (bl2) {
            if (bl) {
                this.getWorld().sendEntityStatus(this, EntityStatuses.BLOCK_WITH_SHIELD);
            } else {
                this.getWorld().sendEntityDamage(this, source);
            }
            if (!(source.isIn(DamageTypeTags.NO_IMPACT) || bl && !(amount > 0.0f))) {
                this.scheduleVelocityUpdate();
            }
            if (entity2 != null && !source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                double d = entity2.getX() - this.getX();
                double e = entity2.getZ() - this.getZ();
                while (d * d + e * e < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    e = (Math.random() - Math.random()) * 0.01;
                }
                this.takeKnockback(0.4f, d, e);
                if (!bl) {
                    this.tiltScreen(d, e);
                }
            }
        }
        if (this.isDead()) {
            if (!this.tryUseTotem(source)) {
                SoundEvent soundEvent = this.getDeathSound();
                if (bl2 && soundEvent != null) {
                    this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
                }
                this.onDeath(source);
            }
        } else if (bl2) {
            this.playHurtSound(source);
        }
        boolean bl4 = bl3 = !bl || amount > 0.0f;
        if (bl3) {
            this.lastDamageSource = source;
            this.lastDamageTime = this.getWorld().getTime();
        }
        if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity) {
            Criteria.ENTITY_HURT_PLAYER.trigger(((ServerPlayerEntity) (Object) this), source, f, amount, bl);
//            if (g > 0.0f && g < 3.4028235E37f) {
//                ((ServerPlayerEntity) (Object) this).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(g * 10.0f));
//            }
        }
        if (entity2 instanceof ServerPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity) entity2, this, source, f, amount, bl);
        }
        return bl3;
    }

    /**
     * @author TheRedBrain
     * @reason
     */
    @Overwrite
    public float modifyAppliedDamage(DamageSource source, float amount) {
        int i;
        int j;
        float f;
        float g;
        float h;
        if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            return amount;
        }
        if (this.hasStatusEffect(StatusEffects.RESISTANCE) && !source.isIn(DamageTypeTags.BYPASSES_RESISTANCE) && (h = (g = amount) - (amount = Math.max((f = amount * (float) (j = 25 - (i = (this.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5))) / 25.0f, 0.0f))) > 0.0f && h < 3.4028235E37f) {
            if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) (Object) this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(h * 10.0f));
            } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(h * 10.0f));
            }
        }
        if (amount <= 0.0f) {
            return 0.0f;
        }
        if (source.isIn(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            return amount;
        }
        boolean feather_falling_trinket_equipped = false;
        if (source.isIn(DamageTypeTags.IS_FALL)) {
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this);
            if (trinkets.isPresent()) {
                Predicate<ItemStack> predicate = stack -> stack.isIn(Tags.FEATHER_FALLING_TRINKETS);
                feather_falling_trinket_equipped = trinkets.get().isEquipped(predicate);
            }
        }
        i = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source) + (feather_falling_trinket_equipped ? 12 : 0);
        if (i > 0) {
            amount = DamageUtil.getInflictedDamage(amount, i);
        }
        return amount;
    }

    /**
     * @author TheRedBrain
     * @reason
     */
    @Overwrite
    public void applyDamage(DamageSource source, float amount) {
        LivingEntity attacker = null;
        if (source.getAttacker() instanceof LivingEntity) {
            attacker = (LivingEntity) source.getAttacker();
        }
        if (this.isInvulnerableTo(source)) {
            return;
        }

        if (!source.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
            this.damageArmor(source, amount);
        }
        float vanilla_amount = 0;

        if (source.isIn(Tags.IS_VANILLA)) {
            if (BetterAdventureModeClient.clientConfig.show_debug_log) {
                BetterAdventureMode.info("This vanilla damage type was used: " + source.getType().toString());
            }
            vanilla_amount = amount;
        }

        float bashing_multiplier = source.isIn(Tags.HAS_BASHING_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_BASHING_DIVISION_OF_0_1) ? 0.1f : 0;
        float bashing_amount = amount * bashing_multiplier;

        float piercing_multiplier = source.isIn(Tags.HAS_PIERCING_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_PIERCING_DIVISION_OF_0_1) ? 0.1f : 0;
        float piercing_amount = amount * piercing_multiplier;

        float slashing_multiplier = source.isIn(Tags.HAS_SLASHING_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_SLASHING_DIVISION_OF_0_1) ? 0.1f : 0;
        float slashing_amount = amount * slashing_multiplier;

        float poison_multiplier = source.isIn(Tags.HAS_POISON_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_POISON_DIVISION_OF_0_1) ? 0.1f : 0;
        float poison_amount = amount * poison_multiplier;

        float fire_multiplier = source.isIn(Tags.HAS_FIRE_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_FIRE_DIVISION_OF_0_1) ? 0.1f : 0;
        float fire_amount = amount * fire_multiplier;

        float frost_multiplier = source.isIn(Tags.HAS_FROST_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_FROST_DIVISION_OF_0_1) ? 0.1f : 0;
        float frost_amount = amount * frost_multiplier;

        float lightning_multiplier = source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_1) ? 1.0f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_9) ? 0.9f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_8) ? 0.8f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_7) ? 0.7f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_6) ? 0.6f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_5) ? 0.5f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_4) ? 0.4f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_3) ? 0.3f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_2) ? 0.2f : source.isIn(Tags.HAS_LIGHTNING_DIVISION_OF_0_1) ? 0.1f : 0;
        float lightning_amount = amount * lightning_multiplier;

        if (attacker != null) {
            bashing_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_BASHING_DAMAGE) + bashing_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_BASHING_DAMAGE));
            piercing_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_PIERCING_DAMAGE) + piercing_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_PIERCING_DAMAGE));
            slashing_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_SLASHING_DAMAGE) + slashing_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_SLASHING_DAMAGE));
            fire_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_FIRE_DAMAGE) + fire_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_FIRE_DAMAGE));
            frost_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_FROST_DAMAGE) + frost_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_FROST_DAMAGE));
            lightning_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_LIGHTNING_DAMAGE) + lightning_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_LIGHTNING_DAMAGE));
            poison_amount = (float) ((attacker.getAttributeValue(EntityAttributesRegistry.ADDITIONAL_POISON_DAMAGE) + poison_amount) * attacker.getAttributeValue(EntityAttributesRegistry.INCREASED_POISON_DAMAGE));
        }

        // region shield blocks
        ItemStack shieldItemStack = this.getOffHandStack();
        if (this.isBlocking() && this.blockedByShield(source) && this.betteradventuremode$getStamina() > 0 && shieldItemStack.getItem() instanceof BasicShieldItem basicShieldItem) {
            boolean tryParry = this.betteradventuremode$canParry() && this.blockingTime <= 20 && this.betteradventuremode$getStamina() >= 2 && source.getAttacker() != null && source.getAttacker() instanceof LivingEntity && ((BasicShieldItem) shieldItemStack.getItem()).canParry();
            // try to parry the attack
            double parryBonus = tryParry ? basicShieldItem.getParryBonus() : 1;
            float blockedBashingDamage = (float) (bashing_amount - basicShieldItem.getPhysicalDamageReduction() * parryBonus);
            float blockedPiercingDamage = (float) (piercing_amount - basicShieldItem.getPhysicalDamageReduction() * parryBonus);
            float blockedSlashingDamage = (float) (slashing_amount - basicShieldItem.getPhysicalDamageReduction() * parryBonus);
            float blockedFireDamage = (float) (fire_amount - basicShieldItem.getFireDamageReduction() * parryBonus);
            float blockedFrostDamage = (float) (frost_amount - basicShieldItem.getFrostDamageReduction() * parryBonus);
            float blockedLightningDamage = (float) (lightning_amount - basicShieldItem.getLightningDamageReduction() * parryBonus);

            this.betteradventuremode$addStamina(tryParry ? -4 : -2);

            if (this.betteradventuremode$getStamina() >= 0) {

                boolean isStaggered = false;
                // apply stagger based on left over damage
                float appliedStagger = (float) Math.max(((bashing_amount - blockedBashingDamage) * 0.75 + (piercing_amount - blockedPiercingDamage) * 0.5 + (slashing_amount - blockedSlashingDamage) * 0.5 + (lightning_amount - blockedLightningDamage) * 0.5), 0);
                if (appliedStagger > 0 && !(this.betteradventuremode$getStaggerLimitMultiplier() == -1 || this.hasStatusEffect(StatusEffectsRegistry.STAGGERED))) {
                    this.betteradventuremode$addPoise(appliedStagger);
                    if (this.betteradventuremode$getPoise() >= this.getMaxHealth() * this.betteradventuremode$getStaggerLimitMultiplier()) {
                        this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.STAGGERED, this.betteradventuremode$getStaggerDuration(), 0, false, false, true));
                        isStaggered = true;
                    }
                }

                // parry was successful
                if (!isStaggered) {
                    bashing_amount -= blockedBashingDamage;
                    piercing_amount -= blockedPiercingDamage;
                    slashing_amount -= blockedSlashingDamage;
                    fire_amount -= blockedFireDamage;
                    frost_amount -= blockedFrostDamage;
                    lightning_amount -= blockedLightningDamage;

                    if (tryParry) {

                        // TODO only one attack can be parried ?
//                        this.blockingTime = this.blockingTime + 20;

                        if (attacker != null) {
                            // attacker is staggered
                            int attackerStaggerDuration = ((DuckLivingEntityMixin) attacker).betteradventuremode$getStaggerDuration();
                            if (attackerStaggerDuration != -1) {
                                attacker.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.STAGGERED, attackerStaggerDuration, 0, false, true, true));
                            }
                        }

                    } else {

                        //
                        if (attacker != null) {
                            attacker.takeKnockback(((BasicShieldItem) shieldItemStack.getItem()).getBlockForce(), attacker.getX() - this.getX(), attacker.getZ() - this.getZ());
                        }

                    }
                    float totalBlockedDamage = blockedBashingDamage + blockedPiercingDamage + blockedSlashingDamage + blockedFireDamage + blockedFrostDamage + blockedLightningDamage;
                    if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity serverPlayerEntity && totalBlockedDamage > 0.0f && totalBlockedDamage < 3.4028235E37f) {
                        serverPlayerEntity.increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(totalBlockedDamage * 10.0f));
                    }

                    this.getWorld().sendEntityStatus(this, EntityStatuses.BLOCK_WITH_SHIELD);
                } else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
                }
            }
        } else {
            this.stopUsingItem();
        }
        // endregion shield blocks

        // region apply resistances/armor
        // armorToughness now directly determines how effective armor is
        // it can not increase armor beyond the initial value
        // effective armor reduces damage by its amount
        // armor can't reduce damage to zero
        float effectiveArmor = this.getArmor() * MathHelper.clamp((float) this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS), 0, 1);
        if (piercing_amount * 1.25 <= effectiveArmor) {
            effectiveArmor -= piercing_amount * 1.25;
            piercing_amount = 0;
        } else {
            // TODO think about this more
            piercing_amount -= effectiveArmor;
            effectiveArmor = 0;
        }

        if (bashing_amount <= effectiveArmor) {
            effectiveArmor -= bashing_amount;
            bashing_amount = 0;
        } else {
            bashing_amount -= effectiveArmor;
            effectiveArmor = 0;
        }

        if (fire_amount <= effectiveArmor) {
            effectiveArmor -= fire_amount;
            fire_amount = 0;
        } else {
            fire_amount -= effectiveArmor;
            effectiveArmor = 0;
        }

        if (slashing_amount <= effectiveArmor) {
            effectiveArmor -= slashing_amount;
            slashing_amount = 0;
        } else {
            slashing_amount -= effectiveArmor;
            slashing_amount = (float) (slashing_amount * 1.25); // slashing damage not blocked by armor deals more damage
            effectiveArmor = 0;
        }

        poison_amount = (float) (poison_amount - (poison_amount * this.getAttributeValue(EntityAttributesRegistry.POISON_RESISTANCE)) / 100);

        fire_amount = (float) (fire_amount - (fire_amount * this.getAttributeValue(EntityAttributesRegistry.FIRE_RESISTANCE)) / 100);

        frost_amount = (float) (frost_amount - (frost_amount * this.getAttributeValue(EntityAttributesRegistry.FROST_RESISTANCE)) / 100);

        lightning_amount = (float) (lightning_amount - (lightning_amount * this.getAttributeValue(EntityAttributesRegistry.LIGHTNING_RESISTANCE)) / 100);
        // endregion apply resistances/armor


        float appliedDamage = piercing_amount + bashing_amount + slashing_amount + vanilla_amount;

        // taking damage interrupts eating food, drinking potions, etc
        if (appliedDamage > 0.0f && !this.isBlocking()) {
            this.stopUsingItem();
        }

        this.getDamageTracker().onDamage(source, appliedDamage);
        this.setHealth(this.getHealth() - appliedDamage);
        if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity serverPlayerEntity && appliedDamage < 3.4028235E37f) {
            serverPlayerEntity.increaseStat(Stats.DAMAGE_TAKEN, Math.round(appliedDamage * 10.0f));
        }
        this.emitGameEvent(GameEvent.ENTITY_DAMAGE);

        // apply stagger
        float appliedStagger = (float) ((bashing_amount * 0.75) + (piercing_amount * 0.5) + (slashing_amount * 0.5) + (lightning_amount * 0.5));
        if (appliedStagger > 0 && !(this.betteradventuremode$getStaggerLimitMultiplier() == -1 || this.hasStatusEffect(StatusEffectsRegistry.STAGGERED))) {
            this.betteradventuremode$addPoise(appliedStagger);
            if (this.betteradventuremode$getPoise() >= this.getMaxHealth() * this.betteradventuremode$getStaggerLimitMultiplier()) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.STAGGERED, this.betteradventuremode$getStaggerDuration(), 0, false, false, true));
            }
        }

        // apply burning
        if (fire_amount > 0) {
            double burnBuildUpMultiplier = this.hasStatusEffect(StatusEffectsRegistry.WET) ? 0.5 : 1;//TODO should wet effect impact burning build up?
            this.betteradventuremode$addBurnBuildUp((float) (fire_amount * burnBuildUpMultiplier));
            if (this.betteradventuremode$getBurnBuildUp() >= 100) {//TODO burn build up threshold
                int burnDuration = 200; // TODO burn duration
                StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffectsRegistry.BURNING);
                if (statusEffectInstance != null) {
                    burnDuration = burnDuration + statusEffectInstance.getDuration();
                }
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.BURNING, burnDuration, 0, false, false, true));
            }
        }

        // apply chilled and frozen
        if (frost_amount > 0) {
            // apply chilled
            int chilledDuration = (int) Math.floor(frost_amount);
            StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffectsRegistry.CHILLED);
            if (statusEffectInstance != null) {
                chilledDuration = chilledDuration + statusEffectInstance.getDuration();
            }
            this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.CHILLED, chilledDuration, 0, false, false, true));

            // apply frozen
            float freezeBuildUp = frost_amount * (this.hasStatusEffect(StatusEffectsRegistry.WET) ? 2 : 1);//TODO should wet effect impact freeze build up?
            if (freezeBuildUp > 0 && this.hasStatusEffect(StatusEffectsRegistry.FROZEN)) {
                this.betteradventuremode$addFreezeBuildUp(freezeBuildUp);
                if (this.betteradventuremode$getFreezeBuildUp() >= 100) {//TODO freeze build up threshold
                    this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FROZEN, 200/*TODO freeze duration*/, 0, false, false, true));
                }
            }
        }

        // apply poison
        if (poison_amount > 0) {
            this.betteradventuremode$addPoisonBuildUp(poison_amount);
            if (this.betteradventuremode$getPoisonBuildUp() >= 100) {//TODO poison build up threshold
                int poisonAmplifier = 0;
                StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffectsRegistry.POISON);
                if (statusEffectInstance != null) {
                    poisonAmplifier = statusEffectInstance.getAmplifier() + 1;
                }
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.POISON, 200/*TODO poison duration*/, poisonAmplifier, false, false, true));
            }
        }

        // apply bleeding
        float appliedBleeding = (float) ((piercing_amount * 0.5) + (slashing_amount * 0.5));
        if (appliedBleeding > 0 && source.isIn(Tags.APPLIES_BLEEDING) && !this.hasStatusEffect(StatusEffectsRegistry.BLEEDING)) {
            this.betteradventuremode$addBleedingBuildUp(appliedBleeding);
            if (this.betteradventuremode$getBleedingBuildUp() >= 100) {//TODO bleeding build up threshold
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.BLEEDING, 200/*TODO bleeding duration*/, 0, false, false, true));
            }
        }

        // apply shocked
        if (lightning_amount > 0) {
            this.betteradventuremode$addShockBuildUp(lightning_amount);
            if (this.betteradventuremode$getShockBuildUp() >= 100) {//TODO shocked build up threshold
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.SHOCKED, 1, 0, false, false, true));
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void betteradventuremode$tick(CallbackInfo ci) {
        if (!this.getWorld().isClient) {
            if (this.betteradventuremode$getPoise() > 0) {
                this.betteradventuremode$addPoise((float) -(this.getMaxHealth() * this.betteradventuremode$getStaggerLimitMultiplier() * 0.01));
            }
            if (this.isBlocking()) {
                this.blockingTime++;
            } else if (this.blockingTime > 0) {
                this.blockingTime = 0;
            }
            this.healthTickTimer++;
            this.staminaTickTimer++;
            this.manaTickTimer++;
            if (this.healthTickTimer >= this.betteradventuremode$getHealthTickThreshold()) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(this.betteradventuremode$getRegeneratedHealth());
                } else if (this.getHealth() > this.getMaxHealth()) {
                    this.setHealth(this.getMaxHealth());
                }
                this.healthTickTimer = 0;
            }

            if (this.betteradventuremode$getStamina() <= 0 && this.delayStaminaRegeneration) {
                this.staminaRegenerationDelayTimer = 0;
                this.delayStaminaRegeneration = false;
            }
            if (this.betteradventuremode$getStamina() > 0 && !this.delayStaminaRegeneration) {
                this.delayStaminaRegeneration = true;
            }
            if (this.staminaRegenerationDelayTimer <= this.betteradventuremode$getStaminaRegenerationDelayThreshold()) {
                this.staminaRegenerationDelayTimer++;
            }

            if (this.staminaTickTimer >= this.betteradventuremode$getStaminaTickThreshold() && staminaRegenerationDelayTimer >= this.betteradventuremode$getStaminaRegenerationDelayThreshold()) {
                if (this.betteradventuremode$getStamina() < this.betteradventuremode$getMaxStamina()) {
                    this.betteradventuremode$addStamina(this.betteradventuremode$getRegeneratedStamina());
                } else if (this.betteradventuremode$getStamina() > this.betteradventuremode$getMaxStamina()) {
                    this.betteradventuremode$setStamina(this.betteradventuremode$getMaxStamina());
                }
                this.staminaTickTimer = 0;
            }

            if (this.manaTickTimer >= this.betteradventuremode$getManaTickThreshold()) {
                if (this.betteradventuremode$getMana() < this.betteradventuremode$getMaxMana()) {
                    ((DuckLivingEntityMixin) this).betteradventuremode$addMana(this.betteradventuremode$getRegeneratedMana());
                } else if (this.betteradventuremode$getMana() > this.betteradventuremode$getMaxMana()) {
                    this.betteradventuremode$setMana(this.betteradventuremode$getMaxMana());
                }
                this.manaTickTimer = 0;
            }

            StatusEffectInstance burningStatusEffectInstance = this.getStatusEffect(StatusEffectsRegistry.BURNING);
            if (burningStatusEffectInstance != null && this.isTouchingWaterOrRain()) {
                int oldBurnDuration = burningStatusEffectInstance.getDuration();
                this.removeStatusEffect(StatusEffectsRegistry.BURNING);
                this.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.BURNING, oldBurnDuration - 5, 0, false, false, true));
            }
        }
    }

    @Override
    public boolean doesRenderOnFire() {
        return super.doesRenderOnFire() || (this.hasStatusEffect(StatusEffectsRegistry.BURNING) && !this.isSpectator());
    }

    @Override
    public int betteradventuremode$getStaminaRegenerationDelayThreshold() {
        return 60;
    }

    @Override
    public int betteradventuremode$getHealthTickThreshold() {
        return 20;
    }

    @Override
    public int betteradventuremode$getStaminaTickThreshold() {
        return 20;
    }

    @Override
    public int betteradventuremode$getManaTickThreshold() {
        return 20;
    }

    @Override
    public float betteradventuremode$getRegeneratedHealth() {
        return this.betteradventuremode$getHealthRegeneration();
    }

    @Override
    public float betteradventuremode$getRegeneratedStamina() {
        return this.betteradventuremode$getStaminaRegeneration();
    }

    @Override
    public float betteradventuremode$getRegeneratedMana() {
        return this.betteradventuremode$getManaRegeneration();
    }

    @Override
    public void betteradventuremode$addBleedingBuildUp(float amount) {
        float f = this.betteradventuremode$getBleedingBuildUp();
        this.betteradventuremode$setBleedingBuildUp(f + amount);
    }

    @Override
    public float betteradventuremode$getBleedingBuildUp() {
        return this.dataTracker.get(BLEEDING_BUILD_UP);
    }

    @Override
    public void betteradventuremode$setBleedingBuildUp(float bleedingBuildUp) {
        this.dataTracker.set(BLEEDING_BUILD_UP, MathHelper.clamp(bleedingBuildUp, 0, this.getMaxHealth()));
    }

    @Override
    public void betteradventuremode$addBurnBuildUp(float amount) {
        float f = this.betteradventuremode$getBurnBuildUp();
        this.betteradventuremode$setBurnBuildUp(f + amount);
    }

    @Override
    public float betteradventuremode$getBurnBuildUp() {
        return this.dataTracker.get(BURN_BUILD_UP);
    }

    @Override
    public void betteradventuremode$setBurnBuildUp(float burnBuildUp) {
        this.dataTracker.set(BURN_BUILD_UP, MathHelper.clamp(burnBuildUp, 0, this.getMaxHealth()));
    }

    @Override
    public void betteradventuremode$addFreezeBuildUp(float amount) {
        float f = this.betteradventuremode$getFreezeBuildUp();
        this.betteradventuremode$setFreezeBuildUp(f + amount);
    }

    @Override
    public float betteradventuremode$getFreezeBuildUp() {
        return this.dataTracker.get(FREEZE_BUILD_UP);
    }

    @Override
    public void betteradventuremode$setFreezeBuildUp(float freezeBuildUp) {
        this.dataTracker.set(FREEZE_BUILD_UP, MathHelper.clamp(freezeBuildUp, 0, this.getMaxHealth()));
    }

    @Override
    public void betteradventuremode$addPoise(float amount) {
        float f = this.betteradventuremode$getPoise();
        this.betteradventuremode$setPoise(f + amount);
    }

    @Override
    public float betteradventuremode$getPoise() {
        return this.dataTracker.get(POISE);
    }

    @Override
    public void betteradventuremode$setPoise(float poise) {
        this.dataTracker.set(POISE, MathHelper.clamp(poise, 0, this.getMaxHealth()));
    }

    @Override
    public void betteradventuremode$addPoisonBuildUp(float amount) {
        float f = this.betteradventuremode$getPoisonBuildUp();
        this.betteradventuremode$setPoisonBuildUp(f + amount);
    }

    @Override
    public float betteradventuremode$getPoisonBuildUp() {
        return this.dataTracker.get(POISON_BUILD_UP);
    }

    @Override
    public void betteradventuremode$setPoisonBuildUp(float poisonBuildUp) {
        this.dataTracker.set(POISON_BUILD_UP, MathHelper.clamp(poisonBuildUp, 0, this.getMaxHealth()));
    }

    @Override
    public void betteradventuremode$addShockBuildUp(float amount) {
        float f = this.betteradventuremode$getShockBuildUp();
        this.betteradventuremode$setShockBuildUp(f + amount);
    }

    @Override
    public float betteradventuremode$getShockBuildUp() {
        return this.dataTracker.get(SHOCK_BUILD_UP);
    }

    @Override
    public void betteradventuremode$setShockBuildUp(float shockBuildUp) {
        this.dataTracker.set(SHOCK_BUILD_UP, MathHelper.clamp(shockBuildUp, 0, this.getMaxHealth()));
    }

    /**
     * Returns the duration of the Staggered status effect when applied to this entity
     */
    @Override
    public int betteradventuremode$getStaggerDuration() {
        return 20;
    }

    @Override
    public double betteradventuremode$getStaggerLimitMultiplier() {
        return 0.5;
    }

    @Override
    public float betteradventuremode$getHealthRegeneration() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.HEALTH_REGENERATION);
    }

    @Override
    public float betteradventuremode$getManaRegeneration() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MANA_REGENERATION);
    }

    @Override
    public float betteradventuremode$getMaxMana() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MAX_MANA);
    }

    @Override
    public void betteradventuremode$addMana(float amount) {
        float f = this.betteradventuremode$getMana();
        this.betteradventuremode$setMana(f + amount);
        if (amount < 0) {
            this.manaTickTimer = 0;
        }
    }

    @Override
    public float betteradventuremode$getMana() {
        return this.dataTracker.get(MANA);
    }

    @Override
    public void betteradventuremode$setMana(float mana) {
        this.dataTracker.set(MANA, MathHelper.clamp(mana, 0, this.betteradventuremode$getMaxMana()));
    }

    @Override
    public float betteradventuremode$getStaminaRegeneration() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.STAMINA_REGENERATION);
    }

    @Override
    public float betteradventuremode$getMaxStamina() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MAX_STAMINA);
    }

    @Override
    public void betteradventuremode$addStamina(float amount) {
        float f = this.betteradventuremode$getStamina();
        this.betteradventuremode$setStamina(f + amount);
        if (amount < 0) {
            this.staminaTickTimer = 0;
        }
    }

    @Override
    public float betteradventuremode$getStamina() {
        return this.dataTracker.get(STAMINA);
    }

    @Override
    public void betteradventuremode$setStamina(float stamina) {
        this.dataTracker.set(STAMINA, MathHelper.clamp(stamina, -100/*TODO balance min stamina*/, this.betteradventuremode$getMaxStamina()));
    }

    @Override
    public boolean betteradventuremode$canParry() {
        return false;
    }

}
