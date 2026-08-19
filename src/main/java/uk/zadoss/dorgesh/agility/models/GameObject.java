package uk.zadoss.dorgesh.agility.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;

@Getter
@RequiredArgsConstructor
public enum GameObject {
	Turgall(NpcID.DORGESH_MALE_ENGINEER),
	Boiler(ObjectID.DORGESH_OLD_GENERATOR_BOILER),
	Console(ObjectID.DORGESH_OLD_GENERATOR_CONSOLE);
	
	private final int id;
}
