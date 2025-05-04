package dev.shxzu.valium.module.modules.movement.longjump;

import dev.shxzu.valium.module.modules.movement.LongJump;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;

public class DoubleJumpLongJump extends SubMode<LongJump> {
    public DoubleJumpLongJump(String name, LongJump parentModule) {
        super(name, parentModule);
    }

    @Override
    public void onEnable() {
        mc.player.setJumping(false);
        mc.player.jump();
        mc.player.jump();
        getParentModule().toggle();
        super.onEnable();
    }
}
