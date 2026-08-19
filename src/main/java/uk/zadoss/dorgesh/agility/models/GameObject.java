package uk.zadoss.dorgesh.agility.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameObject {
	Turgall(2295),
	Boiler(22635),
	Console(22634);
	
	private final int id;
}
