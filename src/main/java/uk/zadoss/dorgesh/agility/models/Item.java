package uk.zadoss.dorgesh.agility.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.ItemID;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Item {
	Spanner(ItemID.DORGESH_SPANNER, ItemCategory.Tool, -1, -1),
	// Heavy
	Powerbox(ItemID.DORGESH_POWERSTATION_POWERBOX, ItemCategory.Heavy, 1, 2),
	Lever(ItemID.DORGESH_POWERSTATION_LEVER, ItemCategory.Heavy, 2, 3),
	Cog(ItemID.DORGESH_POWERSTATION_COG, ItemCategory.Heavy, 3, 1),
	// Delicate
	Capacitor(ItemID.DORGESH_POWERSTATION_BATTERY, ItemCategory.Delicate, 1, 3),
	Fuse(ItemID.DORGESH_POWERSTATION_FUSE, ItemCategory.Delicate, 2, 1),
	Meter(ItemID.DORGESH_POWERSTATION_METER, ItemCategory.Delicate, 3, 2);
	
	private static final Map<ItemCategory, Map<Integer, Item>> LOOKUP = Arrays.stream(values())
			.collect(
					Collectors.groupingBy(
							Item::getCategory,
							Collectors.toMap(
									Item::getVarBitValue,
									item -> item)
					)
			);
	
	private final int itemId;
	private final ItemCategory category;
	private final int dialogOption;
	private final int varBitValue;
	
	public static @NonNull Item fromVarBit(ItemCategory category, int varBitValue) {
		var item = LOOKUP.getOrDefault(category, Map.of()).getOrDefault(varBitValue, null);
		
		if (item == null)
			throw new IndexOutOfBoundsException("Value " + varBitValue + " is not a valid component index with category " + category);
		
		return item;
	}
	
	public enum ItemCategory {
		Tool,
		Heavy,
		Delicate
	}
}
