package com.github.levoment.overworldspawnteleportcommand;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OverworldSpawnTeleportCommandMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// Register the command to teleport to the Overworld spawn point
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
					dispatcher.register(CommandManager.literal("spawn").executes(OverworldSpawnTeleportCommandMod.this::executeTeleportToOverworldSpawnPointCommand));
				}
		);
	}

	private int executeTeleportToOverworldSpawnPointCommand(CommandContext<ServerCommandSource> objectCommandContext) throws CommandSyntaxException {
		// Get the player that executed the command
		Entity entity = objectCommandContext.getSource().getEntity();
		// Get the Overworld dimension
		ServerWorld overworld = objectCommandContext.getSource().getMinecraftServer().getWorld(World.OVERWORLD);
		// Get the server player that executed the command
		ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
		// Get the Overworld spawn point
		BlockPos overworldSpawnPos = overworld.getSpawnPos();
		// Teleport the player to the Overworld spawn point
		serverPlayerEntity.teleport(overworld, overworldSpawnPos.getX(), overworldSpawnPos.getY(), overworldSpawnPos.getZ(), 0f, 0f);
		return 1;
	}
}
