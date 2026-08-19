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
			description = "Highlights Turgall",
			position = 0
	)
	default boolean highlightTurgall() {
		return true;
	}
	
	@ConfigItem(
			keyName = "turgallColour",
			name = "Turgall Highlight",
			description = "Colour to highlight Turgall with",
			position = 1
	)
	default Color turgallColour() {
		return Color.GREEN;
	}
	
	@ConfigItem(
			keyName = "highlightComponent",
			name = "Highlight Component",
			description = "Highlights the currently requested components",
			position = 2
	)
	default boolean highlightComponent() {
		return false;
	}
	
	@ConfigItem(
			keyName = "componentColour",
			name = "Component Highlight",
			description = "Colour to highlight correct component with",
			position = 3
	)
	default Color componentColour() {
		return Color.CYAN;
	}
	
	@ConfigItem(
			keyName = "textColour",
			name = "Text Colour",
			description = "Colour to highlight correct component text with",
			position = 4
	)
	default Color textHighlightColour() {
		return Color.BLUE;
	}
}
