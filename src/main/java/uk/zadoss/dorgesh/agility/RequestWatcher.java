package uk.zadoss.dorgesh.agility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.eventbus.Subscribe;
import uk.zadoss.dorgesh.agility.events.CourseEntered;
import uk.zadoss.dorgesh.agility.models.Item;
import uk.zadoss.dorgesh.agility.models.Item.ItemCategory;

import javax.inject.Inject;

@Slf4j @RequiredArgsConstructor(onConstructor_ = { @Inject })
public final class RequestWatcher {
	private final Client client;
	private final CourseState state;
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onVarbitChanged(VarbitChanged event) {
		if (!state.isOnCourse())
			return;
		
		var id = event.getVarbitId();
		
		if (id == VarbitID.DORGESH_AGILITY_PART_HEAVY || id == VarbitID.DORGESH_AGILITY_PART_DELICATE) {
			log.debug("update request for {}", id);
			updateRequest();
		}
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onCourseEntered(CourseEntered event) {
		updateRequest();
	}
	
	public void updateRequest() {
		var heavy = client.getVarbitValue(VarbitID.DORGESH_AGILITY_PART_HEAVY);
		var delicate = client.getVarbitValue(VarbitID.DORGESH_AGILITY_PART_DELICATE);
		
		log.info("Items requested - Heavy: {} | Delicate: {}", heavy, delicate);
		
		if (isNotSet(heavy) || isNotSet(delicate)) {
			state.resetItems();
			return;
		}
		
		state.setItems(
				Item.fromVarBit(ItemCategory.Heavy, heavy),
				Item.fromVarBit(ItemCategory.Delicate, delicate)
		);
	}
	
	private boolean isNotSet(int value) {
		return value == 0 || value > 3;
	}
}
