package uk.zadoss.dorgesh.agility;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("dorgeshkaanagilityhelper")
public interface DorgeshKaanAgilityConfig extends Config {
	
	@ConfigItem(
			keyName = "highlightTurgall",
			name = "Highlight Turgall",
			description = "Highlights Turgall"
	)
	default boolean highlightTurgall() {
		return false;
	}
	
	@ConfigItem(
			keyName = "turgallColour",
			name = "Turgall Highlight",
			description = "Colour to highlight Turgall with"
	)
	default Color turgallColour() {
		return Color.GREEN;
	}
	
	@ConfigItem(
			keyName = "highlightComponent",
			name = "Highlight Component",
			description = "Highlights the currently requested components"
	)
	default boolean highlightComponent() {
		return true;
	}
	
	@ConfigItem(
			keyName = "componentColour",
			name = "Component Highlight",
			description = "Colour to highlight correct component with"
	)
	default Color componentColour() {
		return Color.CYAN;
	}
}
