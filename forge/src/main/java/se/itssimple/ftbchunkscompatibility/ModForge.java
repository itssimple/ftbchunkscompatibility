package se.itssimple.ftbchunkscompatibility;
import se.itssimple.ftbchunkscompatibility.util.Reference;
import net.minecraftforge.fml.common.Mod;

@Mod(Reference.MOD_ID)
public class ModForge {

	public ModForge() {
		ModCommon.init();
	}

}