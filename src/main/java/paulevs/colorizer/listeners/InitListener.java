package paulevs.colorizer.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.bhcore.interfaces.CustomSectionData;
import paulevs.bhcore.storage.EnumArraySectionData;
import paulevs.bhcore.storage.SectionDataHandler;
import paulevs.colorizer.enums.BlockColor;

public class InitListener {
	public static int colorDataIndex;
	
	@Entrypoint.ModID("colorizer")
	private ModID modID;
	
	@EventListener
	public void onInit(InitEvent event) {
		colorDataIndex = SectionDataHandler.register(modID.id("color"), InitListener::makeColorArray);
	}
	
	private static CustomSectionData makeColorArray() {
		return new EnumArraySectionData<>(4096, BlockColor.class);
	}
}
