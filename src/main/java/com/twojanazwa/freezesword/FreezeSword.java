package com.twojanazwa.freezesword;

import com.twojanazwa.freezesword.config.ConfigValues;
import com.twojanazwa.freezesword.config.ModConfig;
import com.twojanazwa.freezesword.config.ReloadConfigCommand;
import com.twojanazwa.freezesword.item.FreezeSwordItem;
import com.twojanazwa.freezesword.registry.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import static com.twojanazwa.freezesword.utils.LoreCreator.createLore;

public class FreezeSword implements ModInitializer {

		static {
		ModConfig.load();}

	// =========================================================
	// MOD INFO
	// =========================================================

	public static final String MOD_ID = "freezesword";

	// =========================================================
	// ITEM
	// =========================================================

	public static Item FREEZE_SWORD;

	static {
		Item.Settings settings = new Item.Settings()
				.registryKey(
						RegistryKey.of(
								RegistryKeys.ITEM,
								Identifier.of(MOD_ID, "freeze_sword")
						)
				)
				.component(DataComponentTypes.UNBREAKABLE, Unit.INSTANCE)

				.component(
						DataComponentTypes.LORE,
						new LoreComponent(createLore())
				);


		if(ConfigValues.FIREPROOF) {
			settings = settings.fireproof();
		}



		FREEZE_SWORD = Registry.register(
				Registries.ITEM,
				Identifier.of(MOD_ID, "freeze_sword"),
				new FreezeSwordItem(settings)
		);
	}


	// =========================================================
	// CREATIVE TAB
	// =========================================================

	public static final ItemGroup FREEZESWORD_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			Identifier.of(MOD_ID, "freezesword_group"),

			FabricItemGroup.builder()

					// Icon shown in creative inventory
					.icon(() -> new ItemStack(FREEZE_SWORD))

					// Name of the creative tab
					.displayName(Text.literal("Freeze Sword"))

					// Items inside the tab
					.entries((displayContext, entries) -> {
						entries.add(FREEZE_SWORD);

						// Add future items here
						// entries.add(ANOTHER_ITEM);
					})

					.build()
	);

	// =========================================================
	// MOD INITIALIZATION
	// =========================================================

	@Override
	public void onInitialize() {
		ModSounds.init();
		FreezeManager.init();
		ReloadConfigCommand.init();


		// Protects the sword from void
		if(ConfigValues.VOID_PROTECTION){
			VoidProtectionHandler.init();
		}
	}
}