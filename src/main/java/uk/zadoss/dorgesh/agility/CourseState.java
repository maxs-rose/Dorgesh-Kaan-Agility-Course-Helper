package uk.zadoss.dorgesh.agility;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.EventBus;
import uk.zadoss.dorgesh.agility.events.RequestItemChanged;
import uk.zadoss.dorgesh.agility.models.Item;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public final class CourseState {
	private static final int REGION_ID = 10833;
	
	private final EventBus eventBus;
	
	@Getter @Setter
	private int currentRegion;
	
	@Getter @Setter
	private boolean hasSpanner;
	
	@Getter @Setter
	private int lastObjectInteraction = -1;
	
	@Getter @NonNull
	private Optional<Item> heavyItem = Optional.empty();
	@Getter @NonNull
	private Optional<Item> delicateItem = Optional.empty();
	
	public boolean isOnCourse() {
		return currentRegion == REGION_ID;
	}
	
	public boolean hasItems() {
		return getHeavyItem().isPresent() && getDelicateItem().isPresent();
	}
	
	public void reset() {
		hasSpanner = false;
		resetItems();
	}
	
	public void resetItems() {
		heavyItem = Optional.empty();
		delicateItem = Optional.empty();
		lastObjectInteraction = -1;
		
		log.debug("Items reset");
		eventBus.post(new RequestItemChanged());
	}
	
	public void setItems(Item heavyItem, Item delicateItem) {
		this.heavyItem = Optional.of(heavyItem);
		this.delicateItem = Optional.of(delicateItem);
		lastObjectInteraction = -1;
		
		log.info("Items set - Heavy: {} | Delicate: {}", heavyItem, delicateItem);
		eventBus.post(new RequestItemChanged());
	}
}
