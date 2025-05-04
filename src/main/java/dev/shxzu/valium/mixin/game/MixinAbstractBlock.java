package dev.shxzu.valium.mixin.game;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.impl.world.EventBlockShape;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.class)
public class MixinAbstractBlock {

    @ModifyReturnValue(method = "getCollisionShape", at = @At("RETURN"))
    private VoxelShape getCollisionShapeInject(VoxelShape original, BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (pos == null) {
            return original;
        }
        if(Client.INSTANCE == null || Client.INSTANCE.getEventManager() == null){
            return original;
        }
        EventBlockShape eventBlockShape = new EventBlockShape(state,pos,original);
        Client.INSTANCE.getEventManager().post(eventBlockShape);
        return eventBlockShape.getShape();
    }
}
