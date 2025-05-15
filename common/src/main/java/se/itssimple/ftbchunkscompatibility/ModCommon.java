package se.itssimple.ftbchunkscompatibility;

import se.itssimple.ftbchunkscompatibility.data.Constants;
import se.itssimple.ftbchunkscompatibility.util.Reference;

public class ModCommon {
	public static void init() {
		Constants.LOG.info("Loading {} (ID: {}), version {}", Reference.NAME, Reference.MOD_ID, Reference.VERSION);
		load();
	}

	private static void load() {

	}
}