package paulevs.colorizer.mixin;

import net.minecraft.client.render.RenderList;
import org.lwjgl.opengl.GL13;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.bhcore.rendering.shaders.ShaderProgram;
import paulevs.colorizer.ColorizerClient;

@Mixin(RenderList.class)
public class RenderListMixin {
	@Inject(method = "method_1909()V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glPushMatrix()V", shift = Shift.AFTER))
	private void colorizer_startShaderProgram(CallbackInfo info) {
		ColorizerClient.bindProgram();
	}
	
	@Inject(method = "method_1909()V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glPopMatrix()V", shift = Shift.BEFORE))
	private void colorizer_endShaderProgram(CallbackInfo info) {
		ShaderProgram.unbind();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
}
