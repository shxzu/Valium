package dev.shxzu.valium.module.modules.player;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.render.EventRender3D;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.module.setting.impl.RangeSetting;
import dev.shxzu.valium.utils.math.RandomUtil;
import dev.shxzu.valium.utils.mc.InventoryUtils;
import dev.shxzu.valium.utils.timer.MillisTimer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealer extends Module {
    public static final NumberSetting startDelayValue = new NumberSetting("Start Delay", 0, 1000, 100, 10);
    public static final RangeSetting takeDelayValue = new RangeSetting("Take Delay", 0, 1000, 150, 200, 10);
    public static final RangeSetting closeDelay = new RangeSetting("Close Delay", 0, 1000, 150, 200, 10);
    public static final BooleanSetting onlyImportant = new BooleanSetting("Important only", true);
    public static final BooleanSetting close = new BooleanSetting("Close", true);
    public static final BooleanSetting intave = new BooleanSetting("Intave", false);

    private final MillisTimer startDelay = new MillisTimer();
    private final MillisTimer closeTimer = new MillisTimer();
    private final MillisTimer takeDelay = new MillisTimer();

    private boolean STOP = false;
    private boolean didStuff = false;

    public ChestStealer() {
        super("ChestStealer", "Takes all items from chests", 0, ModuleCategory.PLAYER);
        this.addSettings(startDelayValue, takeDelayValue, closeDelay, onlyImportant, close, intave);
    }

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }

        if (mc.player.currentScreenHandler instanceof GenericContainerScreenHandler chest) {
            if (isContainerEmpty(chest) || (mc.currentScreen.getTitle().getString().toLowerCase().contains("shop") || mc.currentScreen.getTitle().getString().toLowerCase().contains("play") || mc.currentScreen.getTitle().getString().toLowerCase().contains("menu") || mc.currentScreen.getTitle().getString().toLowerCase().contains("kits") || mc.currentScreen.getTitle().getString().toLowerCase().contains("upgrades"))) {
                STOP = true;
            }
            if (STOP) {
                if (didStuff && close.getValue()) {
                    if (closeTimer.hasElapsed((long) RandomUtil.randomBetween(closeDelay.getValueMin(), closeDelay.getValueMax()))) {
                        mc.player.closeHandledScreen();
                        if(intave.getValue()) mc.player.setSneaking(false);
                    }
                }
                return;
            }

            if (!startDelay.hasElapsed((long) startDelayValue.getValue())) {
                takeDelay.reset();
                return;
            }

            for (int i = 0; i < chest.getInventory().size(); i++) {
                Slot slot = chest.getSlot(i);
                if (slot.hasStack() && isAllowed(slot.getStack()) && takeDelay.hasElapsed((long) RandomUtil.randomBetween(takeDelayValue.getValueMin(), takeDelayValue.getValueMax()))) {
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                    if(intave.getValue()) mc.player.setSneaking(true);

                    didStuff = true;

                    takeDelay.reset();
                    closeTimer.reset();
                }
            }
        } else {
            startDelay.reset();
            closeTimer.reset();
            takeDelay.reset();

            STOP = false;
            didStuff = false;
        }
    };

    private boolean isAllowed(ItemStack stack) {
        if (onlyImportant.getValue()) {
            return stack.getItem() instanceof SwordItem || stack.contains(DataComponentTypes.FOOD)
                    || stack.getItem() instanceof ArmorItem || (stack.getItem() instanceof BlockItem && InventoryUtils.isBlockPlaceable(stack))
                    || stack.getItem() instanceof EnderPearlItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof ArrowItem || stack.getItem() instanceof FishingRodItem || stack.getItem() instanceof PotionItem;
        } else {
            return true;
        }
    }

    private boolean isContainerEmpty(GenericContainerScreenHandler container) {
        for (int i = 0; i < (container.getInventory().size() == 90 ? 54 : 27); i++) {
            if (isAllowed(container.getSlot(i).getStack()))
                if (container.getSlot(i).hasStack()) return false;
        }
        return true;
    }
}