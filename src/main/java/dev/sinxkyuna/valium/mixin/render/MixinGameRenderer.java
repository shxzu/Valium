package dev.sinxkyuna.valium.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.module.modules.render.AspectRatio;
import dev.sinxkyuna.valium.module.modules.render.NoCameraFX;
import dev.sinxkyuna.valium.utils.Utils;
import dev.sinxkyuna.valium.utils.render.W2SUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.sinxkyuna.valium.utils.mc.RayTraceUtils.getVectorForRotation;


@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer implements Utils {

    @Shadow
    private float zoom;

    @Shadow
    private float zoomX;

    @Shadow
    private float zoomY;

    @Shadow
    private float viewDistance;

    @Shadow
    public abstract void render(RenderTickCounter tickCounter, boolean tick);

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    void tiltViewWhenHurtInject(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (Client.INSTANCE.getModuleManager().getModule(NoCameraFX.class).isEnabled()) {
            Client.INSTANCE.getModuleManager().getModule(NoCameraFX.class);
            if (NoCameraFX.hurtCam.getValue()) {
                ci.cancel();
            }
        }
    }

    @ModifyExpressionValue(method = "findCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult findCrosshairTargetInject(HitResult original, Entity camera, double blockInteractionRange, double entityInteractionRange, float tickDelta) {
        if (camera != MinecraftClient.getInstance().player) {
            return original;
        }

        if (!Client.INSTANCE.getRotationManager().rotating) {
            return original;
        }

        float yaw = Client.INSTANCE.getRotationManager().yaw;
        float pitch = Client.INSTANCE.getRotationManager().pitch;

        return rayTrace(yaw, pitch, (float) Math.max(blockInteractionRange, entityInteractionRange), tickDelta);
    }

    @ModifyExpressionValue(method = "findCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d findCrosshairTargetInject1(Vec3d original, Entity camera, double blockInteractionRange, double entityInteractionRange, float tickDelta) {
        if (camera != MinecraftClient.getInstance().player) {
            return original;
        }

        if (!Client.INSTANCE.getRotationManager().rotating) {
            return original;
        }

        float yaw = Client.INSTANCE.getRotationManager().yaw;
        float pitch = Client.INSTANCE.getRotationManager().pitch;

        return getRotationVec(yaw, pitch);
    }

    @Inject(method = "getBasicProjectionMatrix", at = @At("TAIL"), cancellable = true)
    public void getBasicProjectionMatrixHook(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        if (Client.INSTANCE.getModuleManager().getModule(AspectRatio.class).isEnabled()) {
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.peek().getPositionMatrix().identity();
            if (zoom != 1.0f) {
                matrixStack.translate(zoomX, -zoomY, 0.0f);
                matrixStack.scale(zoom, zoom, 1.0f);
            }
            Client.INSTANCE.getModuleManager().getModule(AspectRatio.class);
            matrixStack.peek().getPositionMatrix().mul(new Matrix4f().setPerspective((float) (fov * 0.01745329238474369), (float) AspectRatio.ratio.getValue(), 0.05f, viewDistance * 4.0f));
            cir.setReturnValue(matrixStack.peek().getPositionMatrix());
        }
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0), method = "renderWorld")
    private void renderHand(RenderTickCounter tickCounter, CallbackInfo ci) {
        MatrixStack matrixStack = new MatrixStack();
        Camera camera = mc.gameRenderer.getCamera();

        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.peek().getPositionMatrix());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
        RenderSystem.applyModelViewMatrix();

        W2SUtil.matrixProject.set(RenderSystem.getProjectionMatrix());
        W2SUtil.matrixModel.set(RenderSystem.getModelViewMatrix());
        W2SUtil.matrixWorldSpace.set(matrixStack.peek().getPositionMatrix());

        RenderSystem.getModelViewStack().popMatrix();
        RenderSystem.applyModelViewMatrix();
    }

    private BlockHitResult rayTrace(float yaw, float pitch, float reach, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        final Vec3d vec3 = mc.cameraEntity.getCameraPosVec(tickDelta);
        final Vec3d vec4 = getVectorForRotation(yaw, pitch);
        final Vec3d vec5 = vec3.add(vec4.x * reach, vec4.y * reach, vec4.z * reach);

        return mc.world.raycast(new RaycastContext(vec3, vec5, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.cameraEntity));
    }

    public Vec3d getRotationVec(float yaw, float pitch) {
        float yawCos = MathHelper.cos(-yaw * 0.017453292f);
        float yawSin = MathHelper.sin(-yaw * 0.017453292f);
        float pitchCos = MathHelper.cos(pitch * 0.017453292f);
        float pitchSin = MathHelper.sin(pitch * 0.017453292f);

        return new Vec3d(yawSin * pitchCos, -pitchSin, yawCos * pitchCos);
    }
}