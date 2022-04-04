package paulevs.colorizer.mixin;

import net.minecraft.class_66;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.colorizer.ColorizerClient;

@Mixin(class_66.class)
public class Class66Mixin {
	@Shadow public int field_231;
	@Shadow public int field_232;
	@Shadow public int field_233;
	
	@Inject(method = "method_296()V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glNewList(II)V", shift = Shift.AFTER))
	private void colorizer_startShaderProgram(CallbackInfo info) {
		//PlayerBase player = ClientUtil.getMinecraft().player;
		//ColorizerClient.updateAndBind(player);
		//int cy = (field_231 >> 4) - (((int) player.y) >> 4);
		//int cx = (field_232 >> 4) - player.chunkX;
		//int cz = (field_233 >> 4) - player.chunkZ;
		//ColorizerClient.updateAndBind(cx, cy, cz);
		//ColorizerClient.updateAndBind(field_231 >> 4, field_232 >> 4, field_233 >> 4);
		//ColorizerClient.program.bindWithUniforms();
		ColorizerClient.setSectionPos(field_231 >> 4, field_232 >> 4, field_233 >> 4);
	}
	
	@Inject(method = "method_296()V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEndList()V", shift = Shift.BEFORE))
	private void colorizer_endShaderProgram(CallbackInfo info) {
		//ShaderProgram.unbind();
	}
	
	@Inject(method = "method_296()V", at = @At("TAIL"))
	private void colorizer_endShaderProgram2(CallbackInfo info) {
		//ShaderProgram.unbind();
	}
	
	/*@Inject(method = "method_304()Z", at = @At("HEAD"))
	private void method_304(CallbackInfoReturnable<Boolean> info) {
		//System.out.println(field_231);
		PlayerBase player = ClientUtil.getMinecraft().player;
		int cy = (field_231 >> 4) - (((int) player.y) >> 4);
		int cx = (field_232 >> 4) - player.chunkX;
		int cz = (field_233 >> 4) - player.chunkZ;
		ColorizerClient.update(cx, cy, cz);
	}*/
}
