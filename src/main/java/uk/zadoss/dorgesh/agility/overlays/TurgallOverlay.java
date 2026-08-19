package uk.zadoss.dorgesh.agility.overlays;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import uk.zadoss.dorgesh.agility.CourseState;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgilityConfig;
import uk.zadoss.dorgesh.agility.models.GameObject;

import javax.inject.Inject;

import java.awt.*;

@Slf4j
public final class TurgallOverlay extends Overlay {
	private final Client client;
	private final DorgeshKaanAgilityConfig config;
	private final CourseState state;
	
	@Inject
	public TurgallOverlay(Client client, DorgeshKaanAgilityConfig config, CourseState state) {
		this.client = client;
		this.config = config;
		this.state = state;
		
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
		setPosition(OverlayPosition.DYNAMIC);
	}
	
	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.highlightTurgall() || !state.isOnCourse())
			return null;
		
		client.getLocalPlayer().getWorldView().npcs()
				.stream()
				.filter(npc -> npc.getId() == GameObject.Turgall.getId())
				.findFirst()
				.ifPresent(npc -> OverlayUtil.renderPolygon(graphics, npc.getConvexHull(), config.turgallColour()));
		
		return null;
	}
}
