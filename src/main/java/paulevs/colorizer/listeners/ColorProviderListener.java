package paulevs.colorizer.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColours;
import net.modificationstation.stationapi.api.client.event.colour.item.ItemColoursRegisterEvent;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import paulevs.bhcore.util.MathUtil;
import paulevs.colorizer.enums.BlockColor;

public class ColorProviderListener {
	@EventListener
	public void onColorsRegister(ItemColoursRegisterEvent event) {
		ItemColours itemColors = event.getItemColours();
		for (BlockColor color: BlockColor.getColors()) {
			int rgb = color.getRGB();
			
			float r = (rgb >> 16) & 255;
			float g = (rgb >> 8) & 255;
			float b = rgb & 255;
			
			r = MathUtil.clamp(r * 0.9F + 255 * 0.2F, 0, 255);
			g = MathUtil.clamp(g * 0.9F + 255 * 0.2F, 0, 255);
			b = MathUtil.clamp(b * 0.9F + 255 * 0.2F, 0, 255);
			
			final int rgbCorrected = (int) r << 16 | (int) g << 8 | (int) b;
			
			ItemColourProvider provider = (stack, tintIndex) -> tintIndex == 0 ? -1 : rgbCorrected;
			itemColors.register(provider, (ItemConvertible) ItemListener.brushByColor.get(color));
			
			provider = (stack, tintIndex) -> rgbCorrected;
			itemColors.register(provider, (ItemConvertible) ItemListener.dyeByColor.get(color));
		}
	}
}
