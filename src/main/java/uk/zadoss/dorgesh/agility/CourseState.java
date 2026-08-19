package uk.zadoss.dorgesh.agility;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import uk.zadoss.dorgesh.agility.models.Item;

import javax.inject.Singleton;

import java.util.Optional;

@Slf4j
@Singleton
public final class CourseState {
	private static final int REGION_ID = 10833;
	
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
	}
	
	public void setItems(Item heavyItem, Item delicateItem) {
		this.heavyItem = Optional.of(heavyItem);
		this.delicateItem = Optional.of(delicateItem);
		lastObjectInteraction = -1;
		
		log.debug("Items set - Heavy: {} | Delicate: {}", heavyItem, delicateItem);
	}
}
