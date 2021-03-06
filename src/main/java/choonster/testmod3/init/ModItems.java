package choonster.testmod3.init;

import choonster.testmod3.TestMod3;
import choonster.testmod3.capability.pigspawner.InfinitePigSpawner;
import choonster.testmod3.capability.pigspawner.PigSpawnerCapability;
import choonster.testmod3.entity.BlockDetectionArrowEntity;
import choonster.testmod3.entity.ModArrowEntity;
import choonster.testmod3.item.*;
import choonster.testmod3.item.variantgroup.ItemVariantGroup;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.DimensionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModItems {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod3.MODID);

	private static final Set<RegistryObject<ModSpawnEggItem>> SPAWN_EGGS = new HashSet<>();

	private static boolean isInitialised;

	public static final RegistryObject<CuttingAxeItem> WOODEN_AXE = ITEMS.register("wooden_axe",
			() -> new CuttingAxeItem(ItemTier.WOOD, 6.0f, -3.2f, defaultItemProperties())
	);

	public static final RegistryObject<EntityTestItem> ENTITY_TEST = ITEMS.register("entity_test",
			() -> new EntityTestItem(defaultItemProperties())
	);

	/*
	 * A music disc.
	 * <p>
	 * Test for this thread:
	 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2408066-try-creating-a-music-disc-in-my-1-8-mod-please
	 */
	public static final RegistryObject<MusicDiscItem> RECORD_SOLARIS = ITEMS.register("record_solaris",
			() -> new MusicDiscItem(13, ModSoundEvents.RECORD_SOLARIS, defaultItemProperties())
	);

	public static final RegistryObject<HeavyItem> HEAVY = ITEMS.register("heavy",
			() -> new HeavyItem(defaultItemProperties())
	);

	public static final RegistryObject<EntityInteractionTestItem> ENTITY_INTERACTION_TEST = ITEMS.register("entity_interaction_test",
			() -> new EntityInteractionTestItem(defaultItemProperties())
	);

	public static final RegistryObject<BlockDestroyerItem> BLOCK_DESTROYER = ITEMS.register("block_destroyer",
			() -> new BlockDestroyerItem(defaultItemProperties())
	);

	public static final RegistryObject<SubscriptsItem> SUBSCRIPTS = ITEMS.register("subscripts",
			() -> new SubscriptsItem(defaultItemProperties())
	);

	public static final RegistryObject<SuperscriptsItem> SUPERSCRIPTS = ITEMS.register("superscripts",
			() -> new SuperscriptsItem(defaultItemProperties())
	);

	public static final RegistryObject<LastUseTimeModelItem> MODEL_TEST = ITEMS.register("model_test",
			() -> new LastUseTimeModelItem(defaultItemProperties())
	);

	public static final RegistryObject<SnowballLauncherItem> SNOWBALL_LAUNCHER = ITEMS.register("snowball_launcher",
			() -> new SnowballLauncherItem(defaultItemProperties())
	);

	public static final RegistryObject<SlingshotItem> SLINGSHOT = ITEMS.register("slingshot",
			() -> new SlingshotItem(defaultItemProperties())
	);

	public static final RegistryObject<UnicodeTooltipsItem> UNICODE_TOOLTIPS = ITEMS.register("unicode_tooltips",
			() -> new UnicodeTooltipsItem(defaultItemProperties())
	);

	public static final RegistryObject<SwapTestItem> SWAP_TEST_A;

	public static final RegistryObject<SwapTestItem> SWAP_TEST_B;

	static {
		final String swapTestA = "swap_test_a";
		final String swapTestB = "swap_test_b";

		// Initialise the fields with lazy references to the items first,
		// allowing them to be referenced from the constructors below
		SWAP_TEST_A = RegistryObject.of(new ResourceLocation(TestMod3.MODID, swapTestA), ForgeRegistries.ITEMS);
		SWAP_TEST_B = RegistryObject.of(new ResourceLocation(TestMod3.MODID, swapTestB), ForgeRegistries.ITEMS);

		// Then register the items
		ITEMS.register(swapTestA,
				() -> new SwapTestItem(defaultItemProperties(), () -> new ItemStack(SWAP_TEST_B.get()))
		);

		ITEMS.register(swapTestB,
				() -> new SwapTestItem(defaultItemProperties(), () -> new ItemStack(SWAP_TEST_A.get()))
		);
	}

	public static final RegistryObject<BlockDebuggerItem> BLOCK_DEBUGGER = ITEMS.register("block_debugger",
			() -> new BlockDebuggerItem(defaultItemProperties())
	);

	public static final RegistryObject<HarvestSwordItem> WOODEN_HARVEST_SWORD = ITEMS.register("wooden_harvest_sword",
			() -> new HarvestSwordItem(ItemTier.WOOD, HarvestSwordItem.addToolTypes(ItemTier.WOOD, defaultItemProperties()))
	);

	public static final RegistryObject<HarvestSwordItem> DIAMOND_HARVEST_SWORD = ITEMS.register("diamond_harvest_sword",
			() -> new HarvestSwordItem(ItemTier.DIAMOND, HarvestSwordItem.addToolTypes(ItemTier.DIAMOND, defaultItemProperties()))
	);

	public static final RegistryObject<ClearerItem> CLEARER = ITEMS.register("clearer",
			() -> new ClearerItem(defaultItemProperties())
	);

	public static final RegistryObject<ModBowItem> BOW = ITEMS.register("bow",
			() -> new ModBowItem(defaultItemProperties().defaultMaxDamage(384))
	);

	public static final RegistryObject<ModArrowItem> ARROW = ITEMS.register("arrow",
			() -> new ModArrowItem(ModArrowEntity::new, defaultItemProperties())
	);

	public static final RegistryObject<HeightTesterItem> HEIGHT_TESTER = ITEMS.register("height_tester",
			() -> new HeightTesterItem(defaultItemProperties())
	);

	// Capabilities are registered and injected in FMLCommonSetupEvent, which is fired after RegistryEvent.Register.
	// This means that item constructors can't directly reference Capability fields (e.g. CapabilityPigSpawner.PIG_SPAWNER_CAPABILITY).
	@SuppressWarnings("Convert2MethodRef")
	public static final RegistryObject<PigSpawnerItem> PIG_SPAWNER_FINITE = ITEMS.register("pig_spawner_finite",
			() -> new PigSpawnerItem(() -> PigSpawnerCapability.PIG_SPAWNER_CAPABILITY.getDefaultInstance(), defaultItemProperties())
	);

	public static final RegistryObject<PigSpawnerItem> PIG_SPAWNER_INFINITE = ITEMS.register("pig_spawner_infinite",
			() -> new PigSpawnerItem(InfinitePigSpawner::new, defaultItemProperties())
	);

	public static final RegistryObject<ContinuousBowItem> CONTINUOUS_BOW = ITEMS.register("continuous_bow",
			() -> new ContinuousBowItem(defaultItemProperties())
	);

	public static final RegistryObject<RespawnerItem> RESPAWNER = ITEMS.register("respawner",
			() -> new RespawnerItem(defaultItemProperties())
	);

	public static final RegistryObject<LootTableTestItem> LOOT_TABLE_TEST = ITEMS.register("loot_table_test",
			() -> new LootTableTestItem(defaultItemProperties())
	);

	public static final RegistryObject<MaxHealthSetterItem> MAX_HEALTH_SETTER_ITEM = ITEMS.register("max_health_setter_item",
			() -> new MaxHealthSetterItem(defaultItemProperties())
	);

	public static final RegistryObject<MaxHealthGetterItem> MAX_HEALTH_GETTER_ITEM = ITEMS.register("max_health_getter_item",
			() -> new MaxHealthGetterItem(defaultItemProperties())
	);

	public static final RegistryObject<SoundEffectItem> GUN = ITEMS.register("gun",
			() -> new SoundEffectItem(ModSoundEvents.NINE_MM_FIRE, defaultItemProperties())
	);

	public static final RegistryObject<DimensionReplacementItem> DIMENSION_REPLACEMENT = ITEMS.register("dimension_replacement",
			() -> new DimensionReplacementItem(
					defaultItemProperties(),
					Util.make(() -> {
						final ImmutableMap.Builder<RegistryKey<DimensionType>, Supplier<ItemStack>> builder = ImmutableMap.builder();

						builder.put(DimensionType.THE_NETHER, () -> new ItemStack(Items.NETHER_STAR));
						builder.put(DimensionType.THE_END, () -> new ItemStack(Items.ENDER_PEARL));

						return builder.build();
					})
			)
	);

	public static final RegistryObject<SoundEffectItem> SADDLE = ITEMS.register("saddle",
			() -> new SoundEffectItem(ModSoundEvents.ACTION_SADDLE, defaultItemProperties())
	);

	public static final RegistryObject<SlowSwordItem> WOODEN_SLOW_SWORD = ITEMS.register("wooden_slow_sword",
			() -> new SlowSwordItem(ItemTier.WOOD, defaultItemProperties())
	);

	public static final RegistryObject<SlowSwordItem> DIAMOND_SLOW_SWORD = ITEMS.register("diamond_slow_sword",
			() -> new SlowSwordItem(ItemTier.DIAMOND, defaultItemProperties())
	);

	public static final RegistryObject<RitualCheckerItem> RITUAL_CHECKER = ITEMS.register("ritual_checker",
			() -> new RitualCheckerItem(defaultItemProperties())
	);

	public static final RegistryObject<HiddenBlockRevealerItem> HIDDEN_BLOCK_REVEALER = ITEMS.register("hidden_block_revealer",
			() -> new HiddenBlockRevealerItem(defaultItemProperties())
	);

	public static final RegistryObject<Item> NO_MOD_NAME = ITEMS.register("no_mod_name",
			() -> new Item(defaultItemProperties())
	);

	public static final RegistryObject<KeyItem> KEY = ITEMS.register("key",
			() -> new KeyItem(defaultItemProperties())
	);

	public static final RegistryObject<ModArrowItem> BLOCK_DETECTION_ARROW = ITEMS.register("block_detection_arrow",
			() -> new ModArrowItem(BlockDetectionArrowEntity::new, defaultItemProperties())
	);

	public static final RegistryObject<Item> TRANSLUCENT_ITEM = ITEMS.register("translucent_item",
			() -> new Item(defaultItemProperties())
	);

	public static final RegistryObject<EntityKillerItem> ENTITY_KILLER = ITEMS.register("entity_killer",
			() -> new EntityKillerItem(defaultItemProperties())
	);

	public static final RegistryObject<ChunkEnergySetterItem> CHUNK_ENERGY_SETTER = ITEMS.register("chunk_energy_setter",
			() -> new ChunkEnergySetterItem(defaultItemProperties())
	);

	public static final RegistryObject<ChunkEnergyGetterItem> CHUNK_ENERGY_GETTER = ITEMS.register("chunk_energy_getter",
			() -> new ChunkEnergyGetterItem(defaultItemProperties())
	);

	public static final RegistryObject<Item> CHUNK_ENERGY_DISPLAY = ITEMS.register("chunk_energy_display",
			() -> new Item(defaultItemProperties())
	);

	public static final RegistryObject<Item> BEACON_ITEM = ITEMS.register("beacon_item",
			() -> new Item(defaultItemProperties())
	);

	public static final RegistryObject<PotionEffectArmourItem> SATURATION_HELMET = ITEMS.register("saturation_helmet",
			() -> new PotionEffectArmourItem(ArmorMaterial.CHAIN, EquipmentSlotType.HEAD, new EffectInstance(Effects.SATURATION, 1, 0, true, false), defaultItemProperties())
	);

	public static final RegistryObject<EntityCheckerItem> ENTITY_CHECKER = ITEMS.register("entity_checker",
			() -> new EntityCheckerItem(defaultItemProperties())
	);

	public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber",
			() -> new Item(defaultItemProperties())
	);


	public static final RegistryObject<ReplacementArmourItem> REPLACEMENT_HELMET;

	public static final RegistryObject<RestrictedArmourItem> REPLACEMENT_CHESTPLATE = ITEMS.register("replacement_chestplate",
			() -> new RestrictedArmourItem(ModArmourMaterial.REPLACEMENT, EquipmentSlotType.CHEST, defaultItemProperties())
	);

	public static final RegistryObject<RestrictedArmourItem> REPLACEMENT_LEGGINGS = ITEMS.register("replacement_leggings",
			() -> new RestrictedArmourItem(ModArmourMaterial.REPLACEMENT, EquipmentSlotType.LEGS, defaultItemProperties())
	);

	public static final RegistryObject<RestrictedArmourItem> REPLACEMENT_BOOTS = ITEMS.register("replacement_boots",
			() -> new RestrictedArmourItem(ModArmourMaterial.REPLACEMENT, EquipmentSlotType.FEET, defaultItemProperties())
	);

	static {
		REPLACEMENT_HELMET = ITEMS.register("replacement_helmet",
				() -> new ReplacementArmourItem(
						ModArmourMaterial.REPLACEMENT,
						EquipmentSlotType.HEAD,
						defaultItemProperties(),
						ImmutableSet.of(
								() -> {
									final ItemStack chest = new ItemStack(REPLACEMENT_CHESTPLATE.get());
									chest.addEnchantment(Enchantments.SHARPNESS, 1);
									return chest;
								},
								() -> new ItemStack(REPLACEMENT_LEGGINGS.get()),
								() -> new ItemStack(REPLACEMENT_BOOTS.get())
						)
				)
		);
	}

	public static final RegistryObject<FluidStackItem> FLUID_STACK_ITEM = ITEMS.register("fluid_stack_item",
			() -> new FluidStackItem(new Item.Properties())
	);

	public static final RegistryObject<ModSpawnEggItem> PLAYER_AVOIDING_CREEPER_SPAWN_EGG = registerSpawnEgg("player_avoiding_creeper_spawn_egg",
			ModEntities.PLAYER_AVOIDING_CREEPER, 0xda70b, 0
	);

	public static final RegistryObject<ModBucketItem> WOODEN_BUCKET = ITEMS.register("wooden_bucket",
			() -> new ModBucketItem(defaultItemProperties().maxStackSize(16))
	);

	public static final RegistryObject<ModBucketItem> STONE_BUCKET = ITEMS.register("stone_bucket",
			() -> new ModBucketItem(defaultItemProperties().maxStackSize(16))
	);

	
	public static final ItemVariantGroup<VariantsItem.Type, VariantsItem> VARIANTS_ITEMS = ItemVariantGroup.Builder.<VariantsItem.Type, VariantsItem>create(ITEMS)
			.groupName("variants_item")
			.suffix()
			.variants(VariantsItem.Type.values())
			.itemFactory(VariantsItem::new)
			.build();


	/**
	 * Registers the {@link DeferredRegister} instance with the mod event bus.
	 * <p>
	 * This should be called during mod construction.
	 *
	 * @param modEventBus The mod event bus
	 */
	public static void initialise(final IEventBus modEventBus) {
		if (isInitialised) {
			throw new IllegalStateException("Already initialised");
		}

		ITEMS.register(modEventBus);

		isInitialised = true;
	}

	/**
	 * Gets the registered mod spawn eggs.
	 *
	 * @return The spawn egg items
	 */
	public static Collection<? extends Supplier<ModSpawnEggItem>> getSpawnEggs() {
		return Collections.unmodifiableCollection(SPAWN_EGGS);
	}

	/**
	 * Registers a spawn egg item.
	 *
	 * @param name           The registry name of the item
	 * @param entityType     The entity type to spawn
	 * @param primaryColor   The primary colour of the spawn egg
	 * @param secondaryColor The secondary colour of the spawn egg
	 * @return A RegistryObject reference to the item
	 */
	private static RegistryObject<ModSpawnEggItem> registerSpawnEgg(
			final String name, final RegistryObject<? extends EntityType<?>> entityType,
			final int primaryColor, final int secondaryColor
	) {
		final RegistryObject<ModSpawnEggItem> spawnEgg = ITEMS.register(name,
				() -> new ModSpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Properties().group(TestMod3.ITEM_GROUP))
		);

		SPAWN_EGGS.add(spawnEgg);

		return spawnEgg;
	}

	/**
	 * Gets an {@link Item.Properties} instance with the {@link ItemGroup} set to {@link TestMod3#ITEM_GROUP}.
	 *
	 * @return The item properties
	 */
	private static Item.Properties defaultItemProperties() {
		return new Item.Properties().group(TestMod3.ITEM_GROUP);
	}

	@Mod.EventBusSubscriber(modid = TestMod3.MODID, bus = Bus.MOD)
	public static class EventHandler {
		@SubscribeEvent
		public static void commonSetup(final FMLCommonSetupEvent event) {
			event.enqueueWork(() ->
					ModSpawnEggItem.addEggsToEggsMap(getSpawnEggs())
			);
		}
	}
}
