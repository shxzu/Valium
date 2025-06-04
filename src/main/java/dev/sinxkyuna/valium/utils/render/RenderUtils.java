package dev.sinxkyuna.valium.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static net.minecraft.util.Identifier.tryParse;

public final class RenderUtils implements Utils {
    public static VertexSorter vertexSorter;
    public static boolean rendering3D = true;

    public static void unscaledProjection() {
        vertexSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0, Client.mc.getWindow().getFramebufferWidth(), Client.mc.getWindow().getFramebufferHeight(), 0, 1000, 21000), VertexSorter.BY_Z);
        rendering3D = false;
    }

    public static void scaledProjection() {
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0, (float) (Client.mc.getWindow().getFramebufferWidth() / Client.mc.getWindow().getScaleFactor()), (float) (Client.mc.getWindow().getFramebufferHeight() / Client.mc.getWindow().getScaleFactor()), 0, 1000, 21000), vertexSorter);
        rendering3D = true;
    }

    public static void draw3DBox(Matrix4f matrix4f, Box box, Color color) {
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1.0f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.minZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.minZ);

        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.minZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.minZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.minZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.maxZ);

        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix4f, (float) box.minX, (float) box.maxY, (float) box.minZ);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
