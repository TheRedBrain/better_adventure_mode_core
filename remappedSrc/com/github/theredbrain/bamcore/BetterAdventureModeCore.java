package com.github.theredbrain.bamcore;

import com.github.theredbrain.bamcore.config.ServerConfig;
import com.github.theredbrain.bamcore.config.ServerConfigWrapper;
import com.github.theredbrain.bamcore.network.packet.BetterAdventureModeCoreServerPacket;
import com.github.theredbrain.bamcore.registry.*;
import com.github.theredbrain.bamcore.world.DimensionsManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterAdventureModeCore implements ModInitializer {
	public static final String MOD_ID = "bamcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig serverConfig;

	@Override
	public void onInitialize() {
		LOGGER.info("We are going on an adventure!");

		// Config
		AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		serverConfig = ((ServerConfigWrapper)AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig()).server;

		// Packets
		BetterAdventureModeCoreServerPacket.init();

		// Registry
		BlockRegistry.init();
		EntityAttributesRegistry.registerAttributes();
		DimensionsManager.init();
		EntityRegistry.init();
		EventsRegistry.initializeEvents();
		PlayerDungeonsRegistry.init();
		PlayerHousesRegistry.init();
		ItemRegistry.init();
		ItemGroupRegistry.init();
		ScreenHandlerTypesRegistry.registerAll();
		StatusEffectsRegistry.registerEffects();
		GameRulesRegistry.init();
		PredicateRegistry.init();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

}