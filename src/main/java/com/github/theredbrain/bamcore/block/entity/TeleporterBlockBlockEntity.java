package com.github.theredbrain.bamcore.block.entity;
// TODO move to bamdimensions
//import com.github.theredbrain.bamcore.registry.BlockRegistry;
//import com.github.theredbrain.bamcore.registry.EntityRegistry;
//import com.github.theredbrain.bamcore.api.util.BetterAdventureModeCoreStatusEffects;
//import com.github.theredbrain.bamcore.screen.TeleporterBlockScreenHandler;
//import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.effect.StatusEffectInstance;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.Inventories;
//import net.minecraft.inventory.Inventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
//import net.minecraft.screen.ScreenHandler;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.text.Text;
//import net.minecraft.util.collection.DefaultedList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Box;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3i;
//import net.minecraft.world.World;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class TeleporterBlockBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, Inventory {
//
//    private String teleporterName = "teleporterName";
//    private boolean showActivationArea = false;
//    private Vec3i activationAreaDimensions = Vec3i.ZERO;
//    private BlockPos activationAreaPositionOffset = new BlockPos(0, 1, 0);
//    private boolean showAdventureScreen = true;
//    private int dimensionMode = 0;
//    private String outgoingTeleportDimension = "minecraft:overworld";
//    private boolean indirectTeleportationMode = false;
//    private BlockPos outgoingTeleportTeleporterPosition = new BlockPos(0, 0, 0);
//    private BlockPos incomingTeleportPositionOffset = new BlockPos(0, 1, 0);
//    private double incomingTeleportPositionYaw = 0.0;
//    private double incomingTeleportPositionPitch = 0.0;
//
//    private BlockPos outgoingTeleportPosition = new BlockPos(0, 0, 0);
//    private double outgoingTeleportPositionYaw = 0.0;
//    private double outgoingTeleportPositionPitch = 0.0;
//
//    // TODO convert to a map of multiple pairs for bigger dungeons
////    private boolean regenerateDungeon = false;
//    private String targetDungeonStructureIdentifier = "";
//    private BlockPos targetDungeonStructureStartPosition = new BlockPos(0, 0, 0);
//    private int targetDungeonChunkX = 0;
//    private int targetDungeonChunkZ = 0;
//    private BlockPos regenerateTargetDungeonTriggerBlockPosition = new BlockPos(0, 0, 0);
//
//    private boolean consumeKeyItemStack = false;
//    private DefaultedList<ItemStack> requiredKeyItemStack = DefaultedList.ofSize(1, ItemStack.EMPTY);
//    private String teleportButtonLabel = "gui.teleport";
//    private String cancelTeleportButtonLabel = "gui.cancel";
//
//    public TeleporterBlockBlockEntity(BlockPos pos, BlockState state) {
//        super(EntityRegistry.TELEPORTER_BLOCK_ENTITY, pos, state);
////        ItemStack test = new ItemStack(Registries.ITEM.get(new Identifier("")), 1);
//    }
//
//    @Override
//    protected void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        nbt.putString("teleporterName", this.teleporterName);
//        nbt.putBoolean("showActivationArea", this.showActivationArea);
//
//        nbt.putInt("activationAreaDimensionsX", this.activationAreaDimensions.getX());
//        nbt.putInt("activationAreaDimensionsY", this.activationAreaDimensions.getY());
//        nbt.putInt("activationAreaDimensionsZ", this.activationAreaDimensions.getZ());
//
//        nbt.putInt("activationAreaPositionOffsetX", this.activationAreaPositionOffset.getX());
//        nbt.putInt("activationAreaPositionOffsetY", this.activationAreaPositionOffset.getY());
//        nbt.putInt("activationAreaPositionOffsetZ", this.activationAreaPositionOffset.getZ());
//
//        nbt.putInt("dimensionMode", this.dimensionMode);
//        nbt.putString("outgoingTeleportDimension", this.outgoingTeleportDimension);
//
//        nbt.putBoolean("showAdventureScreen", this.showAdventureScreen);
//
//        nbt.putBoolean("indirectTeleportationMode", this.indirectTeleportationMode);
//        nbt.putInt("outgoingTeleportTeleporterPositionX", this.outgoingTeleportTeleporterPosition.getX());
//        nbt.putInt("outgoingTeleportTeleporterPositionY", this.outgoingTeleportTeleporterPosition.getY());
//        nbt.putInt("outgoingTeleportTeleporterPositionZ", this.outgoingTeleportTeleporterPosition.getZ());
//
//        nbt.putInt("incomingTeleportPositionOffsetX", this.incomingTeleportPositionOffset.getX());
//        nbt.putInt("incomingTeleportPositionOffsetY", this.incomingTeleportPositionOffset.getY());
//        nbt.putInt("incomingTeleportPositionOffsetZ", this.incomingTeleportPositionOffset.getZ());
//        nbt.putDouble("incomingTeleportPositionYaw", this.incomingTeleportPositionYaw);
//        nbt.putDouble("incomingTeleportPositionPitch", this.incomingTeleportPositionPitch);
//
//        nbt.putInt("outgoingTeleportPositionX", this.outgoingTeleportPosition.getX());
//        nbt.putInt("outgoingTeleportPositionY", this.outgoingTeleportPosition.getY());
//        nbt.putInt("outgoingTeleportPositionZ", this.outgoingTeleportPosition.getZ());
//        nbt.putDouble("outgoingTeleportPositionYaw", this.outgoingTeleportPositionYaw);
//        nbt.putDouble("outgoingTeleportPositionPitch", this.outgoingTeleportPositionPitch);
//
////        nbt.putBoolean("regenerateDungeon", this.regenerateDungeon);
//
//        nbt.putString("targetDungeonStructureIdentifier", this.targetDungeonStructureIdentifier);
//
//        nbt.putInt("targetDungeonStructureStartPositionX", this.targetDungeonStructureStartPosition.getX());
//        nbt.putInt("targetDungeonStructureStartPositionY", this.targetDungeonStructureStartPosition.getY());
//        nbt.putInt("targetDungeonStructureStartPositionZ", this.targetDungeonStructureStartPosition.getZ());
//
//        nbt.putInt("targetDungeonChunkX", this.targetDungeonChunkX);
//        nbt.putInt("targetDungeonChunkZ", this.targetDungeonChunkZ);
//
//        nbt.putInt("regenerateTargetDungeonTriggerBlockPositionX", this.regenerateTargetDungeonTriggerBlockPosition.getX());
//        nbt.putInt("regenerateTargetDungeonTriggerBlockPositionY", this.regenerateTargetDungeonTriggerBlockPosition.getY());
//        nbt.putInt("regenerateTargetDungeonTriggerBlockPositionZ", this.regenerateTargetDungeonTriggerBlockPosition.getZ());
//
//
//        nbt.putBoolean("consumeKeyItemStack", this.consumeKeyItemStack);
//        Inventories.writeNbt(nbt, this.requiredKeyItemStack);
//
//        nbt.putString("teleportButtonLabel", this.teleportButtonLabel);
//        nbt.putString("cancelTeleportButtonLabel", this.cancelTeleportButtonLabel);
//    }
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        super.readNbt(nbt);
//        this.teleporterName = nbt.getString("teleporterName");
//        this.showActivationArea = nbt.getBoolean("showActivationArea");
//
//        int i = MathHelper.clamp(nbt.getInt("activationAreaDimensionsX"), 0, 48);
//        int j = MathHelper.clamp(nbt.getInt("activationAreaDimensionsY"), 0, 48);
//        int k = MathHelper.clamp(nbt.getInt("activationAreaDimensionsZ"), 0, 48);
//        this.activationAreaDimensions = new Vec3i(i, j, k);
//
//        int l = MathHelper.clamp(nbt.getInt("activationAreaPositionOffsetX"), -48, 48);
//        int m = MathHelper.clamp(nbt.getInt("activationAreaPositionOffsetY"), -48, 48);
//        int n = MathHelper.clamp(nbt.getInt("activationAreaPositionOffsetZ"), -48, 48);
//        this.activationAreaPositionOffset = new BlockPos(l, m, n);
//
//        this.dimensionMode = nbt.getInt("dimensionMode");
//        this.outgoingTeleportDimension = nbt.getString("outgoingTeleportDimension");
//
//        this.showAdventureScreen = nbt.getBoolean("showAdventureScreen");
//
//        this.indirectTeleportationMode = nbt.getBoolean("indirectTeleportationMode");
//        this.outgoingTeleportTeleporterPosition = new BlockPos(
//                nbt.getInt("outgoingTeleportTeleporterPositionX"),
//                nbt.getInt("outgoingTeleportTeleporterPositionY"),
//                nbt.getInt("outgoingTeleportTeleporterPositionZ")
//        );
//
//        this.incomingTeleportPositionOffset = new BlockPos(
//                nbt.getInt("incomingTeleportPositionOffsetX"),
//                nbt.getInt("incomingTeleportPositionOffsetY"),
//                nbt.getInt("incomingTeleportPositionOffsetZ")
//        );
//        this.incomingTeleportPositionYaw = nbt.getDouble("incomingTeleportPositionYaw");
//        this.incomingTeleportPositionPitch = nbt.getDouble("incomingTeleportPositionPitch");
//
//        this.outgoingTeleportPosition = new BlockPos(
//                nbt.getInt("outgoingTeleportPositionX"),
//                nbt.getInt("outgoingTeleportPositionY"),
//                nbt.getInt("outgoingTeleportPositionZ")
//        );
//        this.outgoingTeleportPositionYaw = nbt.getDouble("outgoingTeleportPositionYaw");
//        this.outgoingTeleportPositionPitch = nbt.getDouble("outgoingTeleportPositionPitch");
//
////        this.regenerateDungeon = nbt.getBoolean("regenerateDungeon");
//
//        this.targetDungeonStructureIdentifier = nbt.getString("targetDungeonStructureIdentifier");
//        this.targetDungeonStructureStartPosition = new BlockPos(
//                nbt.getInt("targetDungeonStructureStartPositionX"),
//                nbt.getInt("targetDungeonStructureStartPositionY"),
//                nbt.getInt("targetDungeonStructureStartPositionZ")
//        );
//        this.targetDungeonChunkX = nbt.getInt("targetDungeonChunkX");
//        this.targetDungeonChunkZ = nbt.getInt("targetDungeonChunkZ");
//        this.regenerateTargetDungeonTriggerBlockPosition = new BlockPos(
//                nbt.getInt("regenerateTargetDungeonTriggerBlockPositionX"),
//                nbt.getInt("regenerateTargetDungeonTriggerBlockPositionY"),
//                nbt.getInt("regenerateTargetDungeonTriggerBlockPositionZ")
//        );
//
//        this.consumeKeyItemStack = nbt.getBoolean("consumeKeyItemStack");
//        this.requiredKeyItemStack = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
//        Inventories.readNbt(nbt, this.requiredKeyItemStack);
//
//        this.teleportButtonLabel = nbt.getString("teleportButtonLabel");
//        this.cancelTeleportButtonLabel = nbt.getString("cancelTeleportButtonLabel");
//    }
//
//    public BlockEntityUpdateS2CPacket toUpdatePacket() {
//        return BlockEntityUpdateS2CPacket.create(this);
//    }
//
//    @Override
//    public NbtCompound toInitialChunkDataNbt() {
//        return this.createNbt();
//    }
//
//    public static void tick(World world, BlockPos pos, BlockState state, TeleporterBlockBlockEntity blockEntity) {
//
//        if (!world.isClient && world.getTime() % 20L == 0L) {
//            TeleporterBlockBlockEntity.tryOpenScreenRemotely(world, pos, state, blockEntity);
//        }
//    }
//
//    // TODO rename to a more fitting name
//    private static void tryOpenScreenRemotely(World world, BlockPos pos, BlockState state, TeleporterBlockBlockEntity blockEntity) {
//        if (world.isClient) {
//            return;
//        }
//        if (state.isOf(BlockRegistry.TELEPORTER_BLOCK) && world.getBlockEntity(pos) != null && world.getBlockEntity(pos).getType() == blockEntity.getType()) {
//            BlockPos activationAreaPositionOffset = blockEntity.getActivationAreaPositionOffset();
//            Vec3i activationAreaDimensions = blockEntity.getActivationAreaDimensions();
//            BlockPos activationAreaStart = new BlockPos(pos.getX() + activationAreaPositionOffset.getX(), pos.getY() + activationAreaPositionOffset.getY(), pos.getZ() + activationAreaPositionOffset.getZ());
//            BlockPos activationAreaEnd = new BlockPos(activationAreaStart.getX() + activationAreaDimensions.getX(), activationAreaStart.getY() + activationAreaDimensions.getY(), activationAreaStart.getZ() + activationAreaDimensions.getZ());
//            Box activationArea = new Box(activationAreaStart, activationAreaEnd);
//            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, activationArea);
//            for (PlayerEntity playerEntity : list) {
//                if (!playerEntity.hasStatusEffect(BetterAdventureModeCoreStatusEffects.PORTAL_RESISTANCE_EFFECT)) {
//                    // prevents continuous opening of a screen
//                    playerEntity.setStatusEffect(new StatusEffectInstance(BetterAdventureModeCoreStatusEffects.PORTAL_RESISTANCE_EFFECT, -1), playerEntity);
//                    playerEntity.openHandledScreen(state.createScreenHandlerFactory(world, pos));
//                }
//            }
//        }
//    }
//
//    //region --- getter & setter ---
//    public String getTeleporterName() {
//        return teleporterName;
//    }
//
//    // TODO check if input is valid
//    public boolean setTeleporterName(String teleporterName) {
//        this.teleporterName = teleporterName;
//        return true;
//    }
//
//    public boolean getShowActivationArea() {
//        return showActivationArea;
//    }
//
//    // TODO check if input is valid
//    public boolean setShowActivationArea(boolean showActivationArea) {
//        this.showActivationArea = showActivationArea;
//        return true;
//    }
//
//    public Vec3i getActivationAreaDimensions() {
//        return activationAreaDimensions;
//    }
//
//    // TODO check if input is valid
//    public boolean setActivationAreaDimensions(Vec3i activationAreaDimensions) {
//        this.activationAreaDimensions = activationAreaDimensions;
//        return true;
//    }
//
//    public BlockPos getActivationAreaPositionOffset() {
//        return activationAreaPositionOffset;
//    }
//
//    // TODO check if input is valid
//    public boolean setActivationAreaPositionOffset(BlockPos activationAreaPositionOffset) {
//        this.activationAreaPositionOffset = activationAreaPositionOffset;
//        return true;
//    }
//
//    /**
//     *
//     * @return dimensionMode, true: static, false: dynamic TODO 0: static, 1: dynamic, 2: current
//     */
//    public int getDimensionMode() {
//        return dimensionMode;
//    }
//
//    // TODO check if input is valid
//    public boolean setDimensionMode(int dimensionMode) {
//        this.dimensionMode = dimensionMode;
//        return true;
//    }
//
//    public String getOutgoingTeleportDimension() {
//        return outgoingTeleportDimension;
//    }
//
//    // TODO check if input is valid
//    public boolean setOutgoingTeleportDimension(String outgoingTeleportDimension) {
//        this.outgoingTeleportDimension = outgoingTeleportDimension;
//        return true;
//    }
//
//    public boolean getShowAdventureScreen() {
//        return showAdventureScreen;
//    }
//
//    // TODO check if input is valid
//    public boolean setShowAdventureScreen(boolean showAdventureScreen) {
//        this.showAdventureScreen = showAdventureScreen;
//        return true;
//    }
//
//    public boolean getIndirectTeleportationMode() {
//        return indirectTeleportationMode;
//    }
//
//    // TODO check if input is valid
//    public boolean setIndirectTeleportationMode(boolean indirectTeleportationMode) {
//        this.indirectTeleportationMode = indirectTeleportationMode;
//        return true;
//    }
//
//    public BlockPos getOutgoingTeleportTeleporterPosition() {
//        return outgoingTeleportTeleporterPosition;
//    }
//
//    // TODO check if input is valid
//    public boolean setOutgoingTeleportTeleporterPosition(BlockPos outgoingTeleportTeleporterPosition) {
//        this.outgoingTeleportTeleporterPosition = outgoingTeleportTeleporterPosition;
//        return true;
//    }
//
//    public BlockPos getIncomingTeleportPositionOffset() {
//        return incomingTeleportPositionOffset;
//    }
//
//    // TODO check if input is valid
//    public boolean setIncomingTeleportPositionOffset(BlockPos incomingTeleportPositionOffset) {
//        this.incomingTeleportPositionOffset = incomingTeleportPositionOffset;
//        return true;
//    }
//
//    public double getIncomingTeleportPositionYaw() {
//        return incomingTeleportPositionYaw;
//    }
//
//    // TODO check if input is valid
//    public boolean setIncomingTeleportPositionYaw(double incomingTeleportPositionYaw) {
//        this.incomingTeleportPositionYaw = incomingTeleportPositionYaw;
//        return true;
//    }
//
//    public double getIncomingTeleportPositionPitch() {
//        return incomingTeleportPositionPitch;
//    }
//
//    // TODO check if input is valid
//    public boolean setIncomingTeleportPositionPitch(double incomingTeleportPositionPitch) {
//        this.incomingTeleportPositionPitch = incomingTeleportPositionPitch;
//        return true;
//    }
//
//    public BlockPos getOutgoingTeleportPosition() {
//        return outgoingTeleportPosition;
//    }
//
//    // TODO check if input is valid
//    public boolean setOutgoingTeleportPosition(BlockPos outgoingTeleportPosition) {
//        this.outgoingTeleportPosition = outgoingTeleportPosition;
//        return true;
//    }
//
//    public double getOutgoingTeleportPositionYaw() {
//        return outgoingTeleportPositionYaw;
//    }
//
//    // TODO check if input is valid
//    public boolean setOutgoingTeleportPositionYaw(double outgoingTeleportPositionYaw) {
//        this.outgoingTeleportPositionYaw = outgoingTeleportPositionYaw;
//        return true;
//    }
//
//    public double getOutgoingTeleportPositionPitch() {
//        return outgoingTeleportPositionPitch;
//    }
//
//    // TODO check if input is valid
//    public boolean setOutgoingTeleportPositionPitch(double outgoingTeleportPositionPitch) {
//        this.outgoingTeleportPositionPitch = outgoingTeleportPositionPitch;
//        return true;
//    }
//
//    public String getTargetDungeonStructureIdentifier() {
//        return targetDungeonStructureIdentifier;
//    }
//
//    // TODO check if input is valid
//    public boolean setTargetDungeonStructureIdentifier(String targetDungeonStructureIdentifier) {
//        this.targetDungeonStructureIdentifier = targetDungeonStructureIdentifier;
//        return true;
//    }
//
//    public BlockPos getTargetDungeonStructureStartPosition() {
//        return targetDungeonStructureStartPosition;
//    }
//
//    // TODO check if input is valid
//    public boolean setTargetDungeonStructureStartPosition(BlockPos targetDungeonStructureStartPosition) {
//        this.targetDungeonStructureStartPosition = targetDungeonStructureStartPosition;
//        return true;
//    }
//
//    public int getTargetDungeonChunkX() {
//        return targetDungeonChunkX;
//    }
//
//    // TODO check if input is valid
//    public boolean setTargetDungeonChunkX(int targetDungeonChunkX) {
//        this.targetDungeonChunkX = targetDungeonChunkX;
//        return true;
//    }
//
//    public int getTargetDungeonChunkZ() {
//        return targetDungeonChunkZ;
//    }
//
//    // TODO check if input is valid
//    public boolean setTargetDungeonChunkZ(int targetDungeonChunkZ) {
//        this.targetDungeonChunkZ = targetDungeonChunkZ;
//        return true;
//    }
//
//    public BlockPos getRegenerateTargetDungeonTriggerBlockPosition() {
//        return regenerateTargetDungeonTriggerBlockPosition;
//    }
//
//    // TODO check if input is valid
//    public boolean setRegenerateTargetDungeonTriggerBlockPosition(BlockPos regenerateTargetDungeonTriggerBlockPosition) {
//        this.regenerateTargetDungeonTriggerBlockPosition = regenerateTargetDungeonTriggerBlockPosition;
//        return true;
//    }
//
//    public boolean getConsumeKeyItemStack() {
//        return consumeKeyItemStack;
//    }
//
//    // TODO check if input is valid
//    public boolean setConsumeKeyItemStack(boolean consumeKeyItemStack) {
//        this.consumeKeyItemStack = consumeKeyItemStack;
//        return true;
//    }
//
//    public boolean isKeyItemStackSlotVisible() {
//        return !(requiredKeyItemStack.get(0).isEmpty());
//    }
//
//    public DefaultedList<ItemStack> getRequiredKeyItemStack() {
//        return requiredKeyItemStack;
//    }
//
//    public void setRequiredKeyItemStack(DefaultedList<ItemStack> requiredKeyItemStack) {
//        this.requiredKeyItemStack = requiredKeyItemStack;
//    }
//    public String getCancelTeleportButtonLabel() {
//        return cancelTeleportButtonLabel;
//    }
//
//    // TODO check if input is valid
//    public boolean setCancelTeleportButtonLabel(String cancelTeleportButtonLabel) {
//        this.cancelTeleportButtonLabel = cancelTeleportButtonLabel;
//        return true;
//    }
//
//    public String getTeleportButtonLabel() {
//        return teleportButtonLabel;
//    }
//
//    // TODO check if input is valid
//    public boolean setTeleportButtonLabel(String teleportButtonLabel) {
//        this.teleportButtonLabel = teleportButtonLabel;
//        return true;
//    }
//    //endregion
//
//    @Override
//    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
//        buf.writeBlockPos(pos);
//    }
//
//    @Nullable
//    @Override
//    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
//        if (player.isCreativeLevelTwoOp()) {
//            return new TeleporterBlockScreenHandler(syncId, playerInventory, this, true);
//        } else {
//            return new TeleporterBlockScreenHandler(syncId, playerInventory, this, false);
//        }
//    }
//
//    @Override
//    public Text getDisplayName() {
//        return Text.literal(this.teleporterName);
//    }
//
//    @Override
//    public int size() {
//        return this.requiredKeyItemStack.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        for (ItemStack itemStack : this.requiredKeyItemStack) {
//            if (itemStack.isEmpty()) continue;
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public ItemStack getStack(int slot) {
//        return this.requiredKeyItemStack.get(slot);
//    }
//
//    @Override
//    public ItemStack removeStack(int slot, int amount) {
//        ItemStack itemStack = Inventories.splitStack(this.requiredKeyItemStack, slot, amount);
//        if (!itemStack.isEmpty()) {
//            this.markDirty();
//        }
//        return itemStack;
//    }
//
//    @Override
//    public ItemStack removeStack(int slot) {
//        ItemStack itemStack = this.requiredKeyItemStack.get(slot);
//        if (itemStack.isEmpty()) {
//            return ItemStack.EMPTY;
//        }
//        this.requiredKeyItemStack.set(slot, ItemStack.EMPTY);
//        return itemStack;
//    }
//
//    @Override
//    public void setStack(int slot, ItemStack stack) {
//        this.requiredKeyItemStack.set(slot, stack);
//        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
//            stack.setCount(this.getMaxCountPerStack());
//        }
//        this.markDirty();
//    }
//
//    @Override
//    public boolean canPlayerUse(PlayerEntity player) {
//        return Inventory.canPlayerUse(this, player, 48);
//    }
//
//    @Override
//    public void clear() {
//        this.requiredKeyItemStack.clear();
//        this.markDirty();
//    }
//}
//
