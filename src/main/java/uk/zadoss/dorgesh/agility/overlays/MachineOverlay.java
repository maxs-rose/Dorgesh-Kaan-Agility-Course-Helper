package uk.zadoss.dorgesh.agility.overlays;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.Perspective;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.AsyncBufferedImage;
import uk.zadoss.dorgesh.agility.CourseState;
import uk.zadoss.dorgesh.agility.DorgeshKaanAgilityConfig;
import uk.zadoss.dorgesh.agility.events.CourseExit;
import uk.zadoss.dorgesh.agility.models.Item;

import java.awt.*;
import java.awt.geom.RoundRectangle2D.Float;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class MachineOverlay extends Overlay {
	protected final int machineId;
	protected final Client client;
	protected final DorgeshKaanAgilityConfig config;
	protected final CourseState state;
	protected final ItemManager itemManager;
	
	private Optional<GameObject> gameObject = Optional.empty();
	
	protected abstract Optional<Item> getItem();
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onGameObjectSpawned(GameObjectSpawned event) {
		var object = event.getGameObject();
		
		log.trace("Object spawned: {}", object.getId());
		
		if (object.getId() == machineId) {
			log.debug("Object spawned: {}", object.getId());
			gameObject = Optional.of(object);
		}
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onGameObjectDespawned(GameObjectDespawned event) {
		log.trace("Object despawned: {}", event.getGameObject().getId());
		
		if (event.getGameObject().getId() == machineId) {
			log.debug("Object despawned: {}", event);
			gameObject = Optional.empty();
		}
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onGameStateChanged(GameStateChanged event) {
		if (event.getGameState() == GameState.LOADING)
			gameObject = Optional.empty();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseExit(CourseExit event) {
		gameObject = Optional.empty();
	}
	
	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.highlightComponent() || !state.isOnCourse())
			return null;
		
		if (gameObject.isEmpty())
			return null;
		
		var player = client.getLocalPlayer();
		
		if (gameObject.get().getWorldLocation().getPlane() != player.getWorldLocation().getPlane())
			return null;
		
		if (gameObject.get().getLocalLocation().distanceTo(player.getLocalLocation()) > 600)
			return null;
		
		var item = getItem();
		
		if (item.isEmpty())
			return null;
		
		var image = itemImage(item.get());
		var objectLocation = gameObject.get().getLocalLocation();
		
		var canvasLocation = Perspective.getCanvasImageLocation(client, objectLocation, image, 100);
		
		if (canvasLocation == null)
			return null;
		
		var bg = new Float(canvasLocation.getX(), canvasLocation.getY(), image.getWidth(), image.getHeight(), 8, 8);
		graphics.setColor(config.componentColour());
		graphics.fill(bg);
		graphics.draw(bg);
		
		OverlayUtil.renderImageLocation(graphics, canvasLocation, image);
		
		return null;
	}
	
	private final AsyncBufferedImage itemImage(Item item) {
		return itemManager.getImage(item.getItemId());
	}
}
