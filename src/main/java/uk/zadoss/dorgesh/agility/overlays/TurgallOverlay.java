package uk.zadoss.dorgesh.agility.overlays;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import uk.zadoss.dorgesh.agility.CourseState;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgilityConfig;
import uk.zadoss.dorgesh.agility.models.GameObject;

import javax.inject.Inject;

import java.awt.*;
import java.util.Optional;

@Slf4j
public final class TurgallOverlay extends Overlay {
	private final Client client;
	private final DorgeshKaanAgilityConfig config;
	private final CourseState state;
	
	private Optional<NPC> turgall;
	
	@Inject
	public TurgallOverlay(Client client, DorgeshKaanAgilityConfig config, CourseState state) {
		this.client = client;
		this.config = config;
		this.state = state;
		turgall = Optional.empty();
		
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onNpcSpawned(NpcSpawned event) {
		var npc = event.getNpc();
		
		if (npc.getId() != GameObject.Turgall.getId())
			return;
		
		turgall = Optional.of(npc);
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onNpcDespawned(NpcDespawned event) {
		var npc = event.getNpc();
		
		if (npc.getId() != GameObject.Turgall.getId())
			return;
		
		turgall = Optional.empty();
	}
	
	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.highlightTurgall() || !state.isOnCourse())
			return null;
		
		turgall.ifPresent(t -> {
			if (t.getConvexHull() == null)
				return;
			
			OverlayUtil.renderPolygon(graphics, t.getConvexHull(), config.turgallColour());
		});
		
		return null;
	}
}
