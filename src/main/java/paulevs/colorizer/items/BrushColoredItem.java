package paulevs.colorizer.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import paulevs.bhcore.storage.section.SectionDataHandler;
import paulevs.bhcore.storage.section.arrays.EnumArraySectionData;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;
import paulevs.colorizer.ColorizerClient;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.listeners.InitListener;

public class BrushColoredItem extends TemplateItemBase {
	private final BlockColor color;
	
	public BrushColoredItem(Identifier identifier, BlockColor color) {
		super(identifier);
		setTranslationKey(identifier.modID, identifier.id);
		setMaxStackSize(1);
		setDurability(100);
		this.color = color;
	}
	
	@Override
	public boolean useOnTile(ItemInstance item, PlayerBase player, Level level, int x, int y, int z, int facing) {
		Chunk chunk = level.getChunk(x, z);
		
		EnumArraySectionData<BlockColor> data = SectionDataHandler.getData(chunk, y >> 4, InitListener.colorDataIndex);
		if (data == null) {
			System.out.println("Section is null at " + x + " " + y + " " + z + ". This should not happen!");
			return false;
		}
		
		data.setData(MathUtil.getIndex16(x & 15, y & 15, z & 15), color);
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			updateClient(level, x >> 4, y >> 4, z >> 4);
		}
		ClientUtil.updateBlock(level, x, y, z);
		
		return true;
	}
	
	private void updateClient(Level level, int x, int y, int z) {
		ColorizerClient.updateSection(level, x, y, z);
	}
}
