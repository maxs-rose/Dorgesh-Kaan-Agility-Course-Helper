package uk.zadoss.dorgesh.agility.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.ItemID;

@Getter
@RequiredArgsConstructor
public enum Item {
	Spanner(ItemID.DORGESH_SPANNER, -1),
	// Heavy
	Powerbox(ItemID.DORGESH_POWERSTATION_POWERBOX, 1),
	Lever(ItemID.DORGESH_POWERSTATION_LEVER, 2),
	Cog(ItemID.DORGESH_POWERSTATION_COG, 3),
	// Delicate
	Capacitor(ItemID.DORGESH_POWERSTATION_BATTERY, 1),
	Fuse(ItemID.DORGESH_POWERSTATION_FUSE, 2),
	Meter(ItemID.DORGESH_POWERSTATION_METER, 3);
	
	private final int itemId;
	private final int dialogOption;
}
