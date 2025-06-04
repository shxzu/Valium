package dev.sinxkyuna.valium.module.modules.client;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.render.EventRender2D;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.BooleanSetting;
import dev.sinxkyuna.valium.module.setting.impl.ModeSetting;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;
import dev.sinxkyuna.valium.module.setting.impl.StringSetting;
import dev.sinxkyuna.valium.utils.font.FontManager;
import dev.sinxkyuna.valium.utils.font.fonts.FontRenderer;
import dev.sinxkyuna.valium.utils.mc.ChatFormatting;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;
import dev.sinxkyuna.valium.utils.render.DrawUtils;
import dev.sinxkyuna.valium.utils.render.ThemeUtils;
import dev.sinxkyuna.valium.utils.render.shaders.ShaderUtils;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dev.sinxkyuna.valium.utils.render.ThemeUtils.getThemeColor;

public class Interface extends Module {
    public static final BooleanSetting watermark = new BooleanSetting("Watermark", true);
    public static final ModeSetting watermarkMode = new ModeSetting("Watermark Mode", "Simple", "Simple", "Gamesense");
    public static final StringSetting watermarkText = new StringSetting("Watermark Text", "Valium");
    public static final ModeSetting watermarkSimpleFontMode = new ModeSetting("Watermark Font", "MC", "MC", "Product Sans Regular", "Product Sans Medium", "Product Sans Bold", "Verdana", "SFUI", "Proggy Clean");
    public static final BooleanSetting arrayList = new BooleanSetting("ArrayList", true);
    public static final ModeSetting colorMode = new ModeSetting("Color Mode", "Astolfo", "Astolfo", "Theme");
    public static final ModeSetting fontMode = new ModeSetting("ArrayList Font", "MC", "MC", "Product Sans Regular", "Product Sans Medium", "Product Sans Bold", "Verdana", "SFUI", "Proggy Clean");
    public static final ModeSetting suffixMode = new ModeSetting("Suffix", "Space", "Space", "-", ">", "[ ]");
    public static final BooleanSetting hideVisuals = new BooleanSetting("Hide visuals", true);
    public static final BooleanSetting lowercase = new BooleanSetting("Lowercase", false);
    public static final ModeSetting lineMode = new ModeSetting("Line", "None", "None", "Full", "Rise");
    public static final NumberSetting padding = new NumberSetting("Offset", 0, 5, 0, 1);
    public static final NumberSetting opacity = new NumberSetting("BG Opacity", 0, 255, 80, 1);
    public static final BooleanSetting info = new BooleanSetting("Info", true);
    public static final BooleanSetting bpsCounter = new BooleanSetting("BPS", true);
    public static final BooleanSetting fpsCounter = new BooleanSetting("FPS", true);

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public Interface() {
        super("Interface", "Clients HUD", 0, ModuleCategory.CLIENT);
        addSettings(watermark, watermarkMode, watermarkText, watermarkSimpleFontMode, arrayList, colorMode, fontMode, suffixMode, hideVisuals, lowercase, lineMode, padding, opacity, info, bpsCounter, fpsCounter);
        watermarkMode.addDependency(watermark, true);
        watermarkText.addDependency(watermark, true);

        watermarkSimpleFontMode.addDependency(watermarkMode, "Simple");

        colorMode.addDependency(arrayList, true);
        fontMode.addDependency(arrayList, true);
        suffixMode.addDependency(arrayList, true);
        hideVisuals.addDependency(arrayList, true);
        lowercase.addDependency(arrayList, true);
        lineMode.addDependency(arrayList, true);
        padding.addDependency(arrayList, true);
        opacity.addDependency(arrayList, true);

        bpsCounter.addDependency(info, true);
        fpsCounter.addDependency(info, true);
    }

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (mc.currentScreen != null) {
            return;
        }

        if (watermark.getValue()) {
            switch (watermarkMode.getMode()) {
                case "Simple":
                    if (!watermarkText.getValue().isEmpty()) {
                        String watermark = watermarkText.getValue();

                        char firstCharacter = watermark.charAt(0);
                        String restOfString = watermark.substring(1);

                        String totalWatermarkText = firstCharacter + ChatFormatting.WHITE + restOfString;

                        switch (watermarkSimpleFontMode.getMode()) {
                            case "MC":
                                event.getContext().drawText(mc.textRenderer, totalWatermarkText, 3, 3, ThemeUtils.getMainColor().getRGB(), true);
                                break;
                            default:
                                getCustomFontRenderer(watermarkSimpleFontMode.getMode()).drawString(event.getContext().getMatrices(), totalWatermarkText, 3, 3, ThemeUtils.getMainColor());
                                break;
                        }
                    }
                    break;

                case "Gamesense":
                    String text = "§f" + watermarkText.getValue() + "§rsense §8| §f " + ("FPS") + "§7 " + MinecraftClient.getInstance().getCurrentFps() + " §8 | §f " + getIP();
                    int padding = 2;
                    int offsetX = 4;
                    int offsetY = 4;

                    int textWidth = (int) Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_MEDIUM).getStringWidth(text);
                    int textHeight = (int) Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_MEDIUM).getStringHeight(text);
                    int backgroundWidth = textWidth + padding * 2;
                    int backgroundHeight = textHeight + padding * 2;

                    //outline
                    event.getContext().fill(
                            offsetX - padding - 1,
                            offsetY - padding - 1,
                            offsetX + backgroundWidth + 1,
                            offsetY + backgroundHeight + 1,
                            new Color(96, 96, 96).getRGB()
                    );

                    if (PostProcessing.shouldBlurWaterMark()) {
                        ShaderUtils.drawGradientBlur(event.getContext().getMatrices(), offsetX - padding - 2, offsetY - padding - 2, offsetX + backgroundWidth - (offsetX - padding) + 2, offsetY + backgroundHeight - (offsetY - padding) + 2, 30, ThemeUtils.getMainColor(), ThemeUtils.getSecondColor(), ThemeUtils.getSecondColor(), ThemeUtils.getMainColor());
                    }

                    // background
                    event.getContext().fill(
                            offsetX - padding,
                            offsetY - padding,
                            offsetX + backgroundWidth,
                            offsetY + backgroundHeight,
                            new Color(25, 25, 25).getRGB()
                    );

                    // underline
                    DrawUtils.drawHorizontalGradientRect(event.getContext().getMatrices(), offsetX - padding + 1,
                            offsetY + backgroundHeight - 3,
                            offsetX + backgroundWidth - 1,
                            offsetY + backgroundHeight - 1,
                            ThemeUtils.getMainColor(),
                            ThemeUtils.getSecondColor());


                    // text omg really!
                    Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_MEDIUM).drawString(
                            event.getContext().getMatrices(),
                            text,
                            offsetX,
                            offsetY,
                            ThemeUtils.getMainColor()
                    );
                    break;
            }
        }

        if (arrayList.getValue()) {
            List<Module> enabledModules;
            switch (fontMode.getMode()) {
                case "MC":
                    enabledModules = Client.INSTANCE.getModuleManager().getEnabledModules().stream().sorted(Comparator.comparingDouble(ri -> -mc.textRenderer.getWidth(getFullName(ri)))).collect(Collectors.toList());
                    break;
                default:
                    enabledModules = Client.INSTANCE.getModuleManager().getEnabledModules().stream().sorted(Comparator.comparingDouble(ri -> -getArrayListFontRenderer(fontMode.getMode()).getStringWidth(getFullName(ri)))).collect(Collectors.toList());
                    break;
            }

            int i = padding.getValueInt();
            int totalWidth = event.getWidth();

            // render post proccesing
            for (Module m : enabledModules) {
                if (hideVisuals.getValue() && m.getModuleCategory() == ModuleCategory.RENDER) {
                    continue;
                }

                int color = switch (colorMode.getMode()) {
                    case "Astolfo" -> getAstolfo(i * 3);
                    case "Theme" -> getThemeColor(i).getRGB();
                    default -> 0;
                };

                int moduleWidth;
                int moduleHeight;

                switch (fontMode.getMode()) {
                    case "MC":
                        moduleWidth = mc.textRenderer.getWidth(getFullName(m));
                        moduleHeight = mc.textRenderer.fontHeight;
                        break;
                    default:
                        moduleWidth = (int) getArrayListFontRenderer(fontMode.getMode()).getStringWidth(getFullName(m));
                        moduleHeight = (int) getArrayListFontRenderer(fontMode.getMode()).getStringHeight(getFullName(m));
                        break;
                }

                if (PostProcessing.shouldBlurArrayList()) {
                    ShaderUtils.drawGlow(event.getContext().getMatrices(), totalWidth - moduleWidth - padding.getValueInt() - 2, i - 2, totalWidth - padding.getValueInt() + 2 - (totalWidth - moduleWidth - padding.getValueInt() - 2), i + moduleHeight + 1 - (i - 1), 30, new Color(color));
                }

                i += moduleHeight + 3;
            }

            i = padding.getValueInt();

            // render modules
            for (Module m : enabledModules) {
                if (hideVisuals.getValue() && m.getModuleCategory() == ModuleCategory.RENDER) {
                    continue;
                }

                int color = switch (colorMode.getMode()) {
                    case "Astolfo" -> getAstolfo(i * 3);
                    case "Theme" -> getThemeColor(i).getRGB();
                    default -> 0;
                };

                int moduleWidth;
                int moduleHeight;

                switch (fontMode.getMode()) {
                    case "MC":
                        moduleWidth = mc.textRenderer.getWidth(getFullName(m));
                        moduleHeight = mc.textRenderer.fontHeight;
                        break;
                    default:
                        moduleWidth = (int) getArrayListFontRenderer(fontMode.getMode()).getStringWidth(getFullName(m));
                        moduleHeight = (int) getArrayListFontRenderer(fontMode.getMode()).getStringHeight(getFullName(m));
                        break;
                }

                // module background
                event.getContext().fill(totalWidth - moduleWidth - padding.getValueInt() - 2, i - 2, totalWidth - padding.getValueInt() + 2, i + moduleHeight + 1, new Color(0, 0, 0, opacity.getValueInt()).getRGB());

                // module backbar
                switch (lineMode.getMode()) {
                    case "Full":
                        event.getContext().fill(totalWidth - padding.getValueInt() + 3, i - 2, totalWidth - padding.getValueInt() + 5, i + moduleHeight + 1, color);
                        break;
                    case "Rise":
                        event.getContext().fill(totalWidth - padding.getValueInt() + 3, i - 1, totalWidth - padding.getValueInt() + 5, i + moduleHeight, color);
                        break;
                }

                // module name
                switch (fontMode.getMode()) {
                    case "MC":
                        event.getContext().drawText(mc.textRenderer, getFullName(m), totalWidth - moduleWidth - padding.getValueInt(), i, color, true);
                        break;
                    default:
                        getArrayListFontRenderer(fontMode.getMode()).drawString(event.getContext().getMatrices(), getFullName(m), totalWidth - moduleWidth - padding.getValueInt(), i, new Color(color));
                        break;
                }
                i += moduleHeight + 3;
            }
        }

        if (info.getValue()) {
            String bps = String.valueOf(decimalFormat.format(Math.hypot(mc.player.getX() - mc.player.prevX, mc.player.getZ() - mc.player.prevZ) * 20.0F * PlayerUtil.timer()));
            String fps = String.valueOf(MinecraftClient.getInstance().getCurrentFps());

            int x = 3;

            int lineHeight = mc.textRenderer.fontHeight;

            int y = event.getHeight() - (fpsCounter.getValue() && bpsCounter.getValue() ? 2 * lineHeight : lineHeight) + 7; // Padding of 7 from the bottom

            if (fpsCounter.getValue()) {
                int color = switch (colorMode.getMode()) {
                    case "Astolfo" -> getAstolfo(3);
                    case "Theme" -> getThemeColor(1).getRGB();
                    default -> 0;
                };
                event.getContext().drawText(mc.textRenderer, "FPS: " + fps, x, y, color, true);
                y -= lineHeight;
            }

            if (bpsCounter.getValue()) {
                int color = switch (colorMode.getMode()) {
                    case "Astolfo" -> getAstolfo(6);
                    case "Theme" -> getThemeColor(2).getRGB();
                    default -> 0;
                };
                event.getContext().drawText(mc.textRenderer, "BPS: " + bps, x, y, color, true);
            }
        }
    };

    private FontRenderer getArrayListFontRenderer(String name) {
        return switch (name) {
            case "Product Sans Regular" ->
                    Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_REGULAR);
            case "Product Sans Medium" ->
                    Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_MEDIUM);
            case "Product Sans Bold" ->
                    Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.PRODUCT_SANS_BOLD);
            case "SFUI" -> Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.SFUI);
            case "Verdana" -> Client.INSTANCE.getFontManager().getSize(9, FontManager.Type.VERDANA);
            case "Proggy Clean" -> Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.PROGGY_CLEAN);
            default -> null;
        };
    }

    private FontRenderer getCustomFontRenderer(String name) {
        return switch (name) {
            case "Product Sans Regular" ->
                    Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.PRODUCT_SANS_REGULAR);
            case "Product Sans Medium" ->
                    Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.PRODUCT_SANS_MEDIUM);
            case "Product Sans Bold" ->
                    Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.PRODUCT_SANS_BOLD);
            case "SFUI" -> Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.SFUI);
            case "Verdana" -> Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.VERDANA);
            case "Proggy Clean" -> Client.INSTANCE.getFontManager().getSize(10, FontManager.Type.PROGGY_CLEAN);
            default -> null;
        };
    }

    private String getIP() {
        if (mc.world == null) {
            return "NULL";
        } else {
            if (mc.isInSingleplayer()) {
                return "Singleplayer";
            } else {
                return mc.getCurrentServerEntry().address;
            }
        }
    }

    private String getFullName(Module m) {
        if (lowercase.getValue()) {
            if (m.getSuffix() == null) {
                return m.getName().toLowerCase();
            } else {
                switch (suffixMode.getMode()) {
                    case "Space":
                        return (m.getName() + " " + ChatFormatting.GRAY + m.getSuffix()).toLowerCase();
                    case "-":
                        return (m.getName() + ChatFormatting.GRAY + " - " + m.getSuffix()).toLowerCase();
                    case ">":
                        return (m.getName() + ChatFormatting.GRAY + " > " + m.getSuffix()).toLowerCase();
                    case "[ ]":
                        return (m.getName() + ChatFormatting.DARK_GRAY + " [" + ChatFormatting.GRAY + m.getSuffix() + ChatFormatting.DARK_GRAY + "]".toLowerCase());
                }
            }
        } else {
            if (m.getSuffix() == null) {
                return m.getName();
            } else {
                switch (suffixMode.getMode()) {
                    case "Space":
                        return m.getName() + " " + ChatFormatting.GRAY + m.getSuffix();
                    case "-":
                        return m.getName() + ChatFormatting.GRAY + " - " + m.getSuffix();
                    case ">":
                        return m.getName() + ChatFormatting.GRAY + " > " + m.getSuffix();
                    case "[ ]":
                        return m.getName() + ChatFormatting.DARK_GRAY + " [" + ChatFormatting.GRAY + m.getSuffix() + ChatFormatting.DARK_GRAY + "]";
                }
            }
        }
        return "";
    }

    public static int getAstolfo(int offset) {
        int i = (int) ((System.currentTimeMillis() / 11 + offset) % 360);
        i = (i > 180 ? 360 - i : i) + 180;
        return Color.HSBtoRGB(i / 360f, 0.55f, 1f);
    }
}