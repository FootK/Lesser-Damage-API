package adl.client.renderer.gui.hud;

import adl.adl;
import adl.client.ClientDefenceData;
import adl.utils.RBGUtil;
import adl.utils.TextUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;

import static adl.entity.ModAttributes.getMaxLives;


public class LivesStatusRenderer {


    public static void init() {
        adl.eventBus.addListener(EventPriority.NORMAL, false, RegisterGuiOverlaysEvent.class,
                (ev) -> ev.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "adl_lives", LivesStatusRenderer::renderLives));
    }

    public static void renderLives(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height)
    {
        if (!gui.shouldDrawSurvivalElements())
            return;

        Minecraft minecraft = Minecraft.getInstance();
        int x = (width / 2);
        int y = height;

        minecraft.getProfiler().push("defence");
        RenderSystem.enableBlend();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        TextUtil.drawAString(poseStack, minecraft.font, ClientDefenceData.getEntityLives() + " | " + (int)getMaxLives(minecraft.player), x - 141, y - 31, 1.5f, RBGUtil.RGB(186, 49, 255), TextUtil.StringRenderType.OUTLINED);

        RenderSystem.setShaderTexture(0, new ResourceLocation(adl.MOD_ID, "textures/gui/lives.png"));
        if (getMaxLives(minecraft.player) > 999) {
            x += 12;
        } else if (getMaxLives(minecraft.player) > 99) {
            x += 8;
        } else if (getMaxLives(minecraft.player) > 9) {
            x += 4;
        }

        if (ClientDefenceData.getEntityLives() > 999) {
            x += 14;

        } else if (ClientDefenceData.getEntityLives() > 99) {
            x += 10;

        } else if (ClientDefenceData.getEntityLives() > 9) {
            x += 4;
        }

        GuiComponent.blit(poseStack, x - 179, y - 33, 0, 0, 15, 15,
                15, 15);


        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }
}
