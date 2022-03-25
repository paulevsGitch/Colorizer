package paulevs.colorizer.items;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import paulevs.bhcore.storage.EnumArraySectionData;
import paulevs.bhcore.storage.SectionDataHandler;
import paulevs.bhcore.util.MathUtil;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.listeners.InitListener;

public class BrushItem extends TemplateItemBase {
	public BrushItem(Identifier identifier) {
		super(identifier);
		setTranslationKey(identifier.id);
	}
	
	@Override
	public boolean useOnTile(ItemInstance item, PlayerBase player, Level level, int x, int y, int z, int facing) {
		Chunk chunk = level.getChunk(x, z);
		
		EnumArraySectionData<BlockColor> data = SectionDataHandler.getData(chunk, y >> 4, InitListener.colorDataIndex);
		if (data == null) {
			System.out.println("Section is null at " + x + " " + y + " " + z + ". This should not happen!");
			return false;
		}
		
		BlockColor value = BlockColor.getByIndex(level.rand.nextInt(BlockColor.getCount()));
		data.setData(MathUtil.getIndex16(x & 15, y & 15, z & 15), value);
		level.method_202(x, y, z, x, y, z);
		
		return true;
	}
}
