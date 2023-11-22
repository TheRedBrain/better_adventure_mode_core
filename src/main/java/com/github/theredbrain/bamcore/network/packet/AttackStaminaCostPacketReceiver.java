package com.github.theredbrain.bamcore.network.packet;

import com.github.theredbrain.bamcore.api.item.BetterAdventureMode_BasicWeaponItem;
import com.github.theredbrain.bamcore.entity.player.DuckPlayerEntityMixin;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class AttackStaminaCostPacketReceiver implements ServerPlayNetworking.PlayPacketHandler<AttackStaminaCostPacket> {

    @Override
    public void receive(AttackStaminaCostPacket packet, ServerPlayerEntity player, PacketSender responseSender) {

        ItemStack attackHandItemStack = packet.itemStack;

        if (((DuckPlayerEntityMixin) player).bamcore$getStamina() <= 0) {
            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeInt(player.getId());
            ServerPlayNetworking.send(player, BetterAdventureModeCoreServerPacket.CANCEL_ATTACK_PACKET, data);
        } else {
            if (attackHandItemStack.getItem() instanceof BetterAdventureMode_BasicWeaponItem weaponItem) {
                ((DuckPlayerEntityMixin) player).bamcore$addStamina(-(weaponItem.getStaminaCost()));
            }
        }
    }
}
