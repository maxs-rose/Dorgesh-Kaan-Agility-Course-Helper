package uk.zadoss.dorgesh.agility.overlays;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxPriority;
import net.runelite.client.util.AsyncBufferedImage;
import uk.zadoss.dorgesh.agility.CourseState;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgility;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgilityConfig;
import uk.zadoss.dorgesh.agility.events.CourseEntered;
import uk.zadoss.dorgesh.agility.events.CourseExit;
import uk.zadoss.dorgesh.agility.events.RequestItemChanged;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.awt.*;
import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ItemHud {
	private final CourseState state;
	private final InfoBoxManager infoBoxManager;
	private final ItemManager itemManager;
	private final ClientThread clientThread;
	private final DorgeshKaanAgility plugin;
	private final DorgeshKaanAgilityConfig config;
	
	private Optional<InfoBox> heavyItem = Optional.empty();
	private Optional<InfoBox> delicateItem = Optional.empty();
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseEntered(CourseEntered event) {
		refreshOverlay();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseExit(CourseExit event) {
		hideItems();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onRequestItemChanged(RequestItemChanged event) {
		refreshOverlay();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onConfigChanged(ConfigChanged event) {
		if (!event.getGroup().equals(DorgeshKaanAgilityConfig.CONFIG_GROUP))
			return;
		
		clientThread.invokeLater(this::refreshOverlay);
	}
	
	private void refreshOverlay() {
		hideItems();
		showItems();
	}
	
	private void showItems() {
		if (!state.isOnCourse())
			return;
		
		if (!config.showHud())
			return;
		
		state.getHeavyItem().ifPresent(i -> {
			var id = i.getItemId();
			var tooltip = itemManager.getItemComposition(id).getName();
			var image = itemManager.getImage(id);
			
			heavyItem = Optional.of(new ItemInfoBox(image, tooltip, plugin));
			infoBoxManager.addInfoBox(heavyItem.get());
		});
		
		state.getDelicateItem().ifPresent(i -> {
			var id = i.getItemId();
			var tooltip = itemManager.getItemComposition(id).getName();
			var image = itemManager.getImage(id);
			
			delicateItem = Optional.of(new ItemInfoBox(image, tooltip, plugin));
			infoBoxManager.addInfoBox(delicateItem.get());
		});
	}
	
	public void hideItems() {
		heavyItem.ifPresent(i -> {
			infoBoxManager.removeInfoBox(i);
			heavyItem = Optional.empty();
		});
		delicateItem.ifPresent(i -> {
			infoBoxManager.removeInfoBox(i);
			delicateItem = Optional.empty();
		});
	}
	
	private static final class ItemInfoBox extends InfoBox {
		public ItemInfoBox(AsyncBufferedImage image, String tooltip, DorgeshKaanAgility plugin) {
			super(image, plugin);
			setPriority(InfoBoxPriority.HIGH);
			setTooltip(tooltip);
		}
		
		@Override
		public String getText() {
			return "";
		}
		
		@Override
		public Color getTextColor() {
			return null;
		}
	}
}