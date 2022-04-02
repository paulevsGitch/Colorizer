package paulevs.colorizer.listeners;

import com.google.common.collect.Maps;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.items.BrushColoredItem;
import paulevs.colorizer.items.BrushItem;
import paulevs.colorizer.items.DyeItem;
import paulevs.colorizer.items.SpatulaItem;

import java.util.Map;
import java.util.function.Function;

public class ItemListener {
	public static Map<BlockColor, ItemBase> brushByColor = Maps.newEnumMap(BlockColor.class);
	public static Map<BlockColor, ItemBase> dyeByColor = Maps.newEnumMap(BlockColor.class);
	public static ItemBase brush;
	public static ItemBase spatula;
	
	@Entrypoint.ModID("colorizer")
	private ModID modID;
	
	@EventListener
	public void onInit(ItemRegistryEvent event) {
		brush = register(event.registry, "brush", BrushItem::new);
		spatula = register(event.registry, "spatula", SpatulaItem::new);
		
		for (BlockColor color: BlockColor.getColors()) {
			dyeByColor.put(color, register(event.registry, "dye_" + color.getName(), DyeItem::new));
			Identifier brushID = modID.id("brush_" + color.getName());
			BrushColoredItem coloredBrush = new BrushColoredItem(brushID, color);
			event.registry.register(brushID, coloredBrush);
			brushByColor.put(color, coloredBrush);
		}
	}
	
	private ItemBase register(ItemRegistry registry, String name, Function<Identifier, ItemBase> constructor) {
		Identifier id = modID.id(name);
		ItemBase item = constructor.apply(id);
		registry.register(id, item);
		return item;
	}
}
