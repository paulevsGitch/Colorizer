package paulevs.colorizer.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.colorizer.items.BrushItem;
import paulevs.colorizer.items.SpatulaItem;

import java.util.function.Function;

public class ItemListener {
	@Entrypoint.ModID("colorizer")
	private ModID modID;
	
	@EventListener
	public void onInit(ItemRegistryEvent event) {
		register(event.registry, "brush", BrushItem::new);
		register(event.registry, "spatula", SpatulaItem::new);
	}
	
	private void register(ItemRegistry registry, String name, Function<Identifier, ItemBase> constructor) {
		Identifier id = modID.id(name);
		registry.register(id, constructor.apply(id));
	}
}
