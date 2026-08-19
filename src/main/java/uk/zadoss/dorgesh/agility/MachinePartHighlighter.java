package uk.zadoss.dorgesh.agility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;
import uk.zadoss.dorgesh.agility.models.GameObject;

import javax.inject.Inject;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public final class MachinePartHighlighter {
	private final Client client;
	private final DorgeshKaanAgilityConfig config;
	private final ClientThread clientThread;
	private final CourseState state;
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (!state.isOnCourse() || !state.hasItems())
			return;
		
		state.setLastObjectInteraction(event.getId());
	}
	
	@Subscribe
	@SuppressWarnings("unused")
	public void onWidgetLoaded(WidgetLoaded event) {
		if (!state.isOnCourse() || !state.hasItems())
			return;
		
		if (event.getGroupId() != InterfaceID.CHATMENU)
			return;
		
		clientThread.invokeLater(this::DialogOptions);
	}
	
	private void DialogOptions() {
		var widget = client.getWidget(InterfaceID.Chatmenu.OPTIONS);
		
		if (widget == null)
			return;
		
		var lastInteract = state.getLastObjectInteraction();
		
		for (var child : widget.getDynamicChildren()) {
			log.debug("Child: {} - {} - {}", child, child.getIndex(), child.getText());
			
			state.getHeavyItem().ifPresent(i -> {
				if (lastInteract != GameObject.Boiler.getId())
					return;
				
				if (child.getIndex() != i.getIndex())
					return;
				
				highlightOption(child);
			});
			
			state.getDelicateItem().ifPresent(i -> {
				if (lastInteract != GameObject.Console.getId())
					return;
				
				if (child.getIndex() != i.getIndex())
					return;
				
				highlightOption(child);
			});
		}
	}
	
	private void highlightOption(Widget widget) {
		var plainText = Text.removeTags(widget.getText());
		var fomratted = String.format("(%s) %s", widget.getIndex(), plainText);
		widget.setText(ColorUtil.wrapWithColorTag(fomratted, config.textHighlightColour()));
	}
}
