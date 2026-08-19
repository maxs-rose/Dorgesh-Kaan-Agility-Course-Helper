package uk.zadoss.dorgesh.agility;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import uk.zadoss.dorgesh.agility.events.CourseEntered;
import uk.zadoss.dorgesh.agility.events.CourseExit;
import uk.zadoss.dorgesh.agility.overlays.BoilerOverlay;
import uk.zadoss.dorgesh.agility.overlays.ConsoleOverlay;
import uk.zadoss.dorgesh.agility.overlays.TurgallOverlay;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
		name = "Dorgesh-Kaan Agility Course Helper",
		description = "Provides helpful information for the Dorgesh-Kaan Agility Course",
		tags = { "agility", "course", "helper", "dorgesh-kaan", "dorgesh", "kaan", "dorgeshkaan" }
)
public final class DorgeshKaanAgility extends Plugin {
	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private EventBus eventBus;
	@Inject
	private CourseState courseState;
	@Inject
	private TurgallOverlay turgallOverlay;
	@Inject
	private ConsoleOverlay consoleOverlay;
	@Inject
	private BoilerOverlay boilerOverlay;
	@Inject
	private MachinePartHighlighter partHighlighter;
	@Inject
	private RequestWatcher requestWatcher;
	
	@Override
	protected void startUp() {
		eventBus.register(consoleOverlay);
		eventBus.register(boilerOverlay);
		eventBus.register(partHighlighter);
		eventBus.register(requestWatcher);
		overlayManager.add(turgallOverlay);
		overlayManager.add(consoleOverlay);
		overlayManager.add(boilerOverlay);
	}
	
	@Override
	protected void shutDown() {
		overlayManager.remove(turgallOverlay);
		overlayManager.remove(consoleOverlay);
		overlayManager.remove(boilerOverlay);
		eventBus.unregister(requestWatcher);
		eventBus.unregister(consoleOverlay);
		eventBus.unregister(boilerOverlay);
		eventBus.unregister(partHighlighter);
		
		courseState.reset();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onGameTick(GameTick tick) {
		var previousRegion = courseState.getCurrentRegion();
		courseState.setCurrentRegion(client.getLocalPlayer().getWorldLocation().getRegionID());
		
		if (previousRegion == courseState.getCurrentRegion())
			return;
		
		log.debug("Current region id: {}", courseState.getCurrentRegion());
		
		if (courseState.isOnCourse())
			eventBus.post(new CourseEntered());
		else
			eventBus.post(new CourseExit());
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseEntered(CourseEntered courseExit) {
		log.debug("Entered Dongesh-Kaan Agility Course");
		courseState.reset();
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseExit(CourseExit courseExit) {
		log.debug("Left Dongesh-Kaan Agility Course");
		courseState.reset();
	}
	
	@Provides
	@SuppressWarnings("unused")
	DorgeshKaanAgilityConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(DorgeshKaanAgilityConfig.class);
	}
}
