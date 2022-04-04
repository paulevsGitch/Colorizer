package paulevs.colorizer.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.colorizer.ColorizerClient;

@Mixin(InGame.class)
public abstract class InGameMixin {
	@Inject(method = "<init>(Lnet/minecraft/client/Minecraft;)V", at = @At("TAIL"))
	private void test_onInit(Minecraft minecraft, CallbackInfo info) {
		ColorizerClient.init();
	}
	
	@Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", shift = Shift.AFTER))
	private void test_renderForeground(float f, boolean flag, int i, int j, CallbackInfo info) {
		ColorizerClient.bindTexture();
		renderSquare(0, 0, 64, 64);
	}
	
	@Unique
	private void renderSquare(int x, int y, int width, int height) {
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		tessellator.vertex(x, y + height, 0, 0, 1);
		tessellator.vertex(x + width, y + height, 0, 1, 1);
		tessellator.vertex(x + width, y, 0, 1, 0);
		tessellator.vertex(x, y, 0, 0, 0);
		tessellator.draw();
	}
}
