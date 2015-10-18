package com.choonster.testmod3.init;

import com.choonster.testmod3.TestMod3;
import com.choonster.testmod3.block.*;
import com.choonster.testmod3.block.pipe.BlockPipeBasic;
import com.choonster.testmod3.item.block.ItemColoredMod;
import com.choonster.testmod3.tileentity.TileEntitySurvivalCommandBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {

	public static final Set<Block> blocks = new HashSet<>();

	public static BlockWaterGrass waterGrass;
	public static BlockLargeCollisionTest largeCollisionTest;
	public static BlockRightClickTest rightClickTest;
	public static BlockClientPlayerRightClick clientPlayerRightClick;
	public static BlockRotatableLamp rotatableLamp;
	public static BlockItemCollisionTest itemCollisionTest;
	public static BlockSurvivalCommandBlock survivalCommandBlock;

	public static BlockPipeBasic pipeBasic;

	public static void registerBlocks() {
		waterGrass = registerBlock(new BlockWaterGrass(), ItemColoredMod.class, true);
		largeCollisionTest = registerBlock(new BlockLargeCollisionTest());
		rightClickTest = registerBlock(new BlockRightClickTest());
		clientPlayerRightClick = registerBlock(new BlockClientPlayerRightClick());
		rotatableLamp = registerBlock(new BlockRotatableLamp());
		itemCollisionTest = registerBlock(new BlockItemCollisionTest());
		survivalCommandBlock = registerBlock(new BlockSurvivalCommandBlock());

		pipeBasic = registerBlock(new BlockPipeBasic());
	}

	/**
	 * Register a Block with the default ItemBlock class.
	 *
	 * @param block The Block instance
	 * @param <T>   The Block type
	 * @return The Block instance
	 */
	private static <T extends Block> T registerBlock(T block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName().replaceFirst("tile.", ""));
		blocks.add(block);
		return block;
	}

	/**
	 * Register a Block with a custom ItemBlock class.
	 *
	 * @param block           The Block instance
	 * @param itemClass       The ItemBlock class
	 * @param constructorArgs Arguments to pass to the ItemBlock constructor
	 * @param <T>             The Block type
	 * @return The Block instance
	 */
	private static <T extends Block> T registerBlock(T block, Class<? extends ItemBlock> itemClass, Object... constructorArgs) {
		GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName().replaceFirst("tile.", ""), constructorArgs);
		blocks.add(block);
		return block;
	}

	public static void registerTileEntities() {
		registerTileEntity(TileEntitySurvivalCommandBlock.class, "survivalCommandBlock");
	}

	private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, TestMod3.MODID + ":" + id);
	}
}
