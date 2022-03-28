package paulevs.colorizer.items;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class BrushItem extends TemplateItemBase {
	public BrushItem(Identifier identifier) {
		super(identifier);
		setTranslationKey(identifier.id);
		setMaxStackSize(1);
		setDurability(100);
	}
}
