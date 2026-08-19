package uk.zadoss.dorgesh.agility.overlays;

import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import uk.zadoss.dorgesh.agility.CourseState;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgilityConfig;
import uk.zadoss.dorgesh.agility.models.GameObject;
import uk.zadoss.dorgesh.agility.models.Item;

import javax.inject.Inject;

import java.util.Optional;

public final class BoilerOverlay extends MachineOverlay {
	@Inject
	public BoilerOverlay(Client client, DorgeshKaanAgilityConfig config, CourseState state, ItemManager itemManager) {
		super(GameObject.Boiler.getId(), client, config, state, itemManager);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}
	
	@Override
	protected Optional<Item> getItem() {
		return state.getHeavyItem();
	}
}
