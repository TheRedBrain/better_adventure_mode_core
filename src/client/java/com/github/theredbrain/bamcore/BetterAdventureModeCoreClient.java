package com.github.theredbrain.bamcore;

import com.github.theredbrain.bamcore.config.ClientConfig;
import com.github.theredbrain.bamcore.config.ClientConfigWrapper;
import com.github.theredbrain.bamcore.gui.screen.ingame.TeleporterBlockScreen;
import com.github.theredbrain.bamcore.network.packet.BetterAdventureModeCoreClientPacket;
import com.github.theredbrain.bamcore.registry.EntityRegistry;
import com.github.theredbrain.bamcore.registry.ItemRegistry;
import com.github.theredbrain.bamcore.registry.KeyBindingsRegistry;
import com.github.theredbrain.bamcore.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.bamcore.render.block.entity.TeleporterBlockBlockEntityRenderer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class BetterAdventureModeCoreClient implements ClientModInitializer {

    public static ClientConfig clientConfig;

    @Override
    public void onInitializeClient() {
        // Config
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        clientConfig = ((ClientConfigWrapper)AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig()).client;

        // Packets
        BetterAdventureModeCoreClientPacket.init();

        // Registry
        KeyBindingsRegistry.registerKeyBindings();
        registerBlockEntityRenderer();
        registerScreens();
        registerModelPredicateProviders();
    }

    private void registerBlockEntityRenderer() {
        BlockEntityRendererFactories.register(EntityRegistry.TELEPORTER_BLOCK_ENTITY, TeleporterBlockBlockEntityRenderer::new);
    }

    private void registerScreens() {
        HandledScreens.register(ScreenHandlerTypesRegistry.TELEPORTER_BLOCK_SCREEN_HANDLER, TeleporterBlockScreen::new);
    }

    private void registerModelPredicateProviders() {
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_BUCKLER, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_NORMAL_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_TOWER_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }
}