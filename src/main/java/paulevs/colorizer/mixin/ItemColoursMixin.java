package paulevs.colorizer.mixin;

import net.modificationstation.stationapi.api.client.colour.block.BlockColours;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColours;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.bhcore.util.MathUtil;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.listeners.ItemListener;

@Mixin(value = ItemColours.class, remap = false)
public class ItemColoursMixin {
	@Inject(method = "create", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/client/colour/item/ItemColours;register(Lnet/modificationstation/stationapi/api/client/colour/item/ItemColourProvider;[Lnet/modificationstation/stationapi/api/item/ItemConvertible;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void colorizer_create(BlockColours blockColors, CallbackInfoReturnable<ItemColours> info, ItemColours itemColors) {
		for (BlockColor color: BlockColor.getColors()) {
			int rgb = color.getRGB();
			
			float r = (rgb >> 16) & 255;
			float g = (rgb >> 8) & 255;
			float b = rgb & 255;
			
			r = MathUtil.clamp(MathUtil.lerp(r * 0.85F, 255, 0.2F), 0, 255);
			g = MathUtil.clamp(MathUtil.lerp(g * 0.85F, 255, 0.2F), 0, 255);
			b = MathUtil.clamp(MathUtil.lerp(b * 0.85F, 255, 0.2F), 0, 255);
			
			final int rgbCorrected = (int) r << 16 | (int) g << 8 | (int) b;
			
			ItemColourProvider provider = (stack, tintIndex) -> tintIndex == 0 ? -1 : rgbCorrected;
			itemColors.register(provider, (ItemConvertible) ItemListener.brushByColor.get(color));
			
			provider = (stack, tintIndex) -> rgbCorrected;
			itemColors.register(provider, (ItemConvertible) ItemListener.dyeByColor.get(color));
		}
	}
}
