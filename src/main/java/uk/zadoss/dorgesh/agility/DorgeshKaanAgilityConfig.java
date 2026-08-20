package uk.zadoss.dorgesh.agility;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup(DorgeshKaanAgilityConfig.CONFIG_GROUP)
public interface DorgeshKaanAgilityConfig extends Config {
	public final String CONFIG_GROUP = "dorgeshkaanagilityhelper";
	
	
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
	
	@ConfigItem(
			keyName = "showHud",
			name = "Component HUD",
			description = "Show requested components as HUD element",
			position = 5
	)
	default boolean showHud() {
		return true;
	}
}
