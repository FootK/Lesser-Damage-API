package adl.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class TextUtil {

    public static void drawAMessage(PoseStack poseStack, Font fontRenderer, Component msg, float x, float y, float scale, int colour, StringRenderType renderType) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);

        float realX = (x - fontRenderer.width(msg) * scale / 2f) / scale;
        float realY = y / scale;

        if (renderType == StringRenderType.OUTLINED) {
            fontRenderer.draw(poseStack, msg, realX, realY + (1 / scale), 0);
            fontRenderer.draw(poseStack, msg, realX, realY - (1 / scale), 0);
            fontRenderer.draw(poseStack, msg, realX + (1 / scale), realY, 0);
            fontRenderer.draw(poseStack, msg, realX - (1 / scale), realY, 0);
        }

        if (renderType == StringRenderType.DROP_SHADOW) {
            fontRenderer.drawShadow(poseStack, msg, realX, realY, colour);
        }
        else {
            fontRenderer.draw(poseStack, msg, realX, realY, colour);
        }

        poseStack.popPose();
    }

    public static void drawAString(PoseStack poseStack, Font fontRenderer, String msg, float x, float y, float scale, int colour, StringRenderType renderType) {
        drawAMessage(poseStack, fontRenderer, Component.literal(msg), x, y, scale, colour, renderType);
    }

    public enum StringRenderType {
        NORMAL,
        DROP_SHADOW,
        OUTLINED
    }
}
