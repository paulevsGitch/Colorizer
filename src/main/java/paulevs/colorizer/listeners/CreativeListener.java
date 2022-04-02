package paulevs.colorizer.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;
import paulevs.colorizer.enums.BlockColor;

public class CreativeListener {
	public static CreativeTab tab;
	
	@Entrypoint.ModID("colorizer")
	private ModID modID;
	
	@EventListener
	public void onTabInit(TabRegistryEvent event) {
		tab = new SimpleTab(modID.id("colorizer_tab"), (ItemConvertible) ItemListener.brush);
		event.register(tab);
		
		tab.addItem(new ItemInstance(ItemListener.spatula));
		tab.addItem(new ItemInstance(ItemListener.brush));
		
		for (BlockColor color: BlockColor.getColors()) {
			tab.addItem(new ItemInstance(ItemListener.dyeByColor.get(color)));
		}
		
		for (BlockColor color: BlockColor.getColors()) {
			tab.addItem(new ItemInstance(ItemListener.brushByColor.get(color)));
		}
	}
}
