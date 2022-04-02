package paulevs.colorizer.items;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class DyeItem extends TemplateItemBase {
	public DyeItem(Identifier identifier) {
		super(identifier);
		setTranslationKey(identifier.modID, identifier.id);
		setMaxStackSize(64);
	}
}
