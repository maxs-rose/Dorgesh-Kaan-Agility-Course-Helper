package uk.zadoss.dorgesh.agility;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public final class DorgeshKaanAgilityTest {
	public static void main(String[] args) throws Exception {
		//noinspection unchecked
		ExternalPluginManager.loadBuiltin(DorgeshKaanAgility.class);
		RuneLite.main(args);
	}
}
