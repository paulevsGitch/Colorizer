package paulevs.colorizer.mixin;

import net.minecraft.level.BlockView;
import net.minecraft.level.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcore.storage.EnumArraySectionData;
import paulevs.bhcore.storage.SectionDataHandler;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.listeners.InitListener;

@Mixin(targets = {
	"net.minecraft.block.BlockBase",
	"net.minecraft.block.Leaves",
	"net.minecraft.block.Grass",
	"net.minecraft.block.TallGrass"
})
public class BlockColorMixin {
	private Chunk lastChunk;
	
	@Inject(method = "getColourMultiplier(Lnet/minecraft/level/BlockView;III)I", at = @At("HEAD"), cancellable = true)
	private void bhc_getColourMultiplier(BlockView tileView, int x, int y, int z, CallbackInfoReturnable<Integer> info) {
		Chunk chunk = ClientUtil.getClientLevel().getChunk(x, z);
		if (chunk == null) return;
		
		EnumArraySectionData<BlockColor> data = SectionDataHandler.getData(chunk, y >> 4, InitListener.colorDataIndex);
		
		if (data == null) return;
		BlockColor value = data.getData(MathUtil.getIndex16(x & 15, y & 15, z & 15));
		if (value == null) return;
		
		info.setReturnValue(value.getRGB());
	}
}
