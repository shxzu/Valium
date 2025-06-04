package dev.sinxkyuna.valium.mixin.keyboard;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.module.modules.movement.Sprint;
import net.minecraft.client.input.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Input.class)
public class MixinInput {
    @Shadow
    public boolean pressingRight;
    @Shadow
    public boolean pressingLeft;
    @Shadow
    public boolean pressingBack;
    @Shadow
    public boolean pressingForward;
    @Shadow
    public float movementForward;
    @Shadow
    public float movementSideways;
    @Shadow
    public boolean jumping;

    @Shadow
    public boolean sneaking;

    @Inject(method = "hasForwardMovement", cancellable = true, at = @At("RETURN"))
    private void hasForwardMovementInject(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final boolean hasMovement = Math.abs(movementForward) > 1.0E-5F || Math.abs(movementSideways) > 1.0E-5F;

        Client.INSTANCE.getModuleManager().getModule(Sprint.class);
        callbackInfoReturnable.setReturnValue(Sprint.shouldSprintDiagonally() ? hasMovement : callbackInfoReturnable.getReturnValue());
    }
}