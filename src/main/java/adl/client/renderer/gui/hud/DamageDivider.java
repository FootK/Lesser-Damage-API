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

public class DamageDivider {
    public static void init() {
        adl.eventBus.addListener(EventPriority.NORMAL, false, RegisterGuiOverlaysEvent.class,
                (ev) -> ev.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "adl_damage_divisor", DamageDivider::renderDamageDivider));
    }

    public static void renderDamageDivider(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.enableBlend();
        int x = (width / 2);
        int y = height;

        minecraft.getProfiler().push("defence");

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        String string = Integer.toString(ClientDefenceData.getDamageDivider());

        // DamageDivider
        TextUtil.drawAString(poseStack, minecraft.font, ClientDefenceData.getDamageDivider() + "", x - 141, y - 51, 1.5f, RBGUtil.RGB(255, 255, 238), TextUtil.StringRenderType.OUTLINED);

        RenderSystem.setShaderTexture(0, new ResourceLocation(adl.MOD_ID, "textures/gui/damagedivisor.png"));
        GuiComponent.blit(poseStack, x - 165 - string.length() * 4, y - 53, 0, 0, 15, 15,
                15, 15);


        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }

}