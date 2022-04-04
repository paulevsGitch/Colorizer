package paulevs.colorizer.mixin;

import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.colorizer.ColorizerClient;

@Mixin(PlayerBase.class)
public class PlayerBaseMixin {
	@Inject(method = "tick()V", at = @At("HEAD"))
	private void colorizer_onPlayerTick(CallbackInfo info) {
		ColorizerClient.updatePlayer(PlayerBase.class.cast(this));
	}
}
