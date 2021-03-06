package choonster.testmod3.item;

import choonster.testmod3.text.TestMod3Lang;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * An item that checks if the player is standing on obsidian and surrounded by the following pattern of blocks:
 * <p>
 * A A A A A
 * A R R R A
 * A R P R A
 * A R R R A
 * A A A A A
 * <p>
 * Where A is air, R is redstone and P is the player.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/topic/41088-1102-checking-blocks-around-player/
 *
 * @author Choonster
 */
public class RitualCheckerItem extends Item {
	public RitualCheckerItem(final Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand hand) {
		if (!worldIn.isRemote) {
			final ITextComponent textComponent;

			final Optional<BlockPos> invalidPosition = checkRitual(playerIn);
			if (invalidPosition.isPresent()) {
				final BlockPos pos = invalidPosition.get();
				textComponent = new TranslationTextComponent(TestMod3Lang.MESSAGE_RITUAL_CHECKER_FAILURE.getTranslationKey(), pos.getX(), pos.getY(), pos.getZ());
				textComponent.getStyle().setFormatting(TextFormatting.RED);
			} else {
				textComponent = new TranslationTextComponent(TestMod3Lang.MESSAGE_RITUAL_CHECKER_SUCCESS.getTranslationKey());
				textComponent.getStyle().setFormatting(TextFormatting.GREEN);
			}

			playerIn.sendMessage(textComponent, Util.DUMMY_UUID);
		}

		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
	}

	/**
	 * Is the player surrounded by the correct pattern of blocks?
	 *
	 * @param player The command player
	 * @return The first invalid position, if any.
	 */
	private Optional<BlockPos> checkRitual(final PlayerEntity player) {
		final World world = player.getEntityWorld();
		final BlockPos playerPos = player.getPosition();

		// The block under the player must be obsidian
		if (!(world.getBlockState(playerPos.down()).getBlock() == Blocks.OBSIDIAN))
			return Optional.of(playerPos.down());

		// Iterate from -2,0,-2 to +2,0,+2
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				// If this is the player's position, skip it
				if (x == 0 && z == 0) {
					continue;
				}

				final BlockPos pos = playerPos.add(x, 0, z);
				final Block block = world.getBlockState(pos).getBlock();

				if (Math.abs(x) == 2 || Math.abs(z) == 2) { // If this is the outer layer, the block must be air
					if (block != Blocks.AIR) return Optional.of(pos);

				} else { // If this is the inner layer, the block must be redstone
					if (block != Blocks.REDSTONE_WIRE) return Optional.of(pos);

				}
			}
		}

		// All blocks are correct, the ritual is valid
		return Optional.empty();
	}
}
